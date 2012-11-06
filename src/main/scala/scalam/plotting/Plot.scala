package scalam.plotting

import scala.sys.process._
import scalam.m.ast._
import scalax.file.Path
import scala.collection.mutable.Map
import scala.collection.mutable.ListBuffer
import scalam.io.Saveable
import scala.language.implicitConversions

class Plot(
  val dataSets: Seq[DataSet],
  title: String,
  xLabel: String,
  yLabel: String,
  grid: Boolean = true,
  legend: Boolean = true,
  fontSize: Int = 10,
  styles: Seq[Style[StyleElement]] = Seq()) {

  def preamble = {
    val df = new java.text.SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss")
    val now = (new java.util.Date(System.currentTimeMillis()))
    Seq(
      DoubleComment("Generated by scalam, v1.0-SNAPSHOT"),
      DoubleComment(df.format(now)))
  }

  class RichDataSet(val id: Identifier, val localPath: Path, val underlying: DataSet)

  val richDataSets = {
    val knownIds = Map[Identifier, Int]()
    def toRich(dataSet: DataSet) = {
      val firstId = Identifier(dataSet.name)
      val finalId = knownIds.get(firstId) match {
        case None => { //firstId is not already used
          knownIds += (firstId -> 1)
          firstId
        }
        case Some(prev) => { // firstId is already in use
          knownIds(firstId) = prev + 1
          Identifier(firstId.name + "_" + prev)
        }
      }
      new RichDataSet(finalId, Path("data") / finalId.name, dataSet)
    }
    dataSets.map(toRich(_))
  }

  def resolveStyles: (Seq[Root], Seq[DataSet => StyleElement]) = {
    val setupAndStyles = styles.map(_.apply(dataSets))
    val setup = setupAndStyles.map(_._1).flatten
    val styleMaps = setupAndStyles.map(_._2)
    (setup, styleMaps)
  }

  def roots: Seq[Root] = {
    import Plot._
    val richDataSets = this.richDataSets

    val (setup, styleMappings) = resolveStyles

    val loads = richDataSets.map(r =>
      m.load(r.id, r.localPath) withComment SimpleComment(r.underlying.label))

    val plots = richDataSets.map { r =>
      val styleElements = styleMappings.map(_.apply(r.underlying))
      m.plot(r.id, styleElements)
    }

    val roots = new ListBuffer[Root]
    roots ++= preamble
    roots ++= loads
    roots ++= setup
    roots += m.newFigure
    roots += m.hold(true)
    roots += m.grid(this.grid)
    roots += m.fontSize(this.fontSize)
    roots += m.title(this.title)
    roots += m.xLabel(this.xLabel)
    roots += m.yLabel(this.yLabel)
    roots ++= plots
    roots += m.legend(dataSets)

    roots.toList
  }

}

object Plot {
  val PlotFileName = "results.m"

  private[this] var counter = -1
  private def next = { counter += 1; counter }

  implicit def plotIsSaveable(plot: Plot) = new Saveable {
    def save(path: scalax.file.Path) = {
      val plotFile = (path / PlotFileName)
      plotFile.createFile(createParents = true, failIfExists = false)
      for (processor <- plotFile.outputProcessor; out = processor.asOutput)
        for (r <- plot.roots) out.write(r.line + "\n")

      for (d <- plot.richDataSets) d.underlying.save(path / d.localPath)
    }
  }

  private object m {
    import scalam.m.ast._

    val On = StringLiteral("on")
    val Off = StringLiteral("off")

    def newFigure = Function(Identifier("figure"))
    def hold(b: Boolean) = Function(Identifier("hold"), if (b) On else Off)
    def grid(show: Boolean) = Function(Identifier("grid"), if (show) On else Off)
    def title(s: String) = Function(Identifier("title"), StringLiteral(s))
    def xLabel(s: String) = Function(Identifier("xlabel"), StringLiteral(s))
    def yLabel(s: String) = Function(Identifier("ylabel"), StringLiteral(s))
    def fontSize(size: Int) = Function(Identifier("set"), Variable(Identifier("gca")), StringLiteral("fontsize"), IntLiteral(size))
    def load(id: Identifier, path: Path) = Assign(id, Function(Identifier("load"), StringLiteral(path.path)))
    def plot(dataSet: Identifier, styleElements: Seq[StyleElement]) = {
      val plot = Identifier("plot")
      val params = Seq(
        IndexMatrix(dataSet, SliceLiteral, IntLiteral(1)),
        IndexMatrix(dataSet, SliceLiteral, IntLiteral(2))) ++
        styleElements.flatMap(e => Seq(e.name, e.expression))
      Function(plot, params: _*)
    }
    def legend(dataSets: Seq[DataSet]) =
      Function(Identifier("legend"), dataSets.map(d => StringLiteral(d.label)): _*)
  }
}