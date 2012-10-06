package scalam.plotting

import scala.sys.process._
import scalam.DataSet
import scalam.m.ast._
import scalax.file.Path

class Plot(
  val dataSets: Seq[DataSet],
  title: String,
  xLabel: String,
  yLabel: String,
  grid: Boolean = true,
  legend: Boolean = true,
  fontSize: Int = 10,
  colorScheme: ColorScheme = Uniform(Black),
  name: String = "plot" + Plot.next) {
  
  val directory = Path(name)

  private case class RichDataSet(underlying: DataSet, localPath: Path)
  private val richDataSets = dataSets.zipWithIndex.map {
    case (d, i) => RichDataSet(d, Path("data") / i.toString)
  }

  val statements: List[Statement] = {
    def data = for ((d, i) <- richDataSets.zipWithIndex.toList) yield {
      val id = Identifier("data" + i)
      val load = Identifier("load")
      Assign(id, Call(load, StringLiteral(d.localPath.path)))
    }

    val figureId = Identifier("fh")
    def figure = {
      val on = StringLiteral("on")
      val off = StringLiteral("off")
      
      val newFigure = Assign(figureId, Call(Identifier("figure")))
      val holdOn = Evaluate(Call(Identifier("hold"), on))
      val grid = Evaluate(Call(Identifier("grid"), if (this.grid) on else off))
      val title = Evaluate(Call(Identifier("title"), StringLiteral(this.title)))
      val xlabel = Evaluate(Call(Identifier("xlabel"), StringLiteral(this.xLabel)))
      val ylabel = Evaluate(Call(Identifier("ylabel"), StringLiteral(this.yLabel)))
      val fontSize = Evaluate(Call(Identifier("set"), Call(Identifier("gca")), StringLiteral("fontsize"), IntLiteral(this.fontSize)))
      List(newFigure, holdOn, grid, title, xlabel, ylabel, fontSize)
    }

    def plot = for (assignment <- data) yield {
      val plot = Identifier("plot")
      Evaluate(
        Call(
          plot,
          IndexMatrix(assignment.variable, SliceLiteral, IntLiteral(1)),
          IndexMatrix(assignment.variable, SliceLiteral, IntLiteral(2))))
    }
    
    def legend = if (this.legend)
      Evaluate(Call(Identifier("legend"), (for (d <- dataSets) yield StringLiteral(d.name)): _*)) :: Nil
      else Nil
      
    def wait = List(Evaluate(Call(Identifier("waitfor"), Variable(figureId))))

    data ::: figure ::: plot ::: legend ::: wait
  }

  def save() = {
    for (d <- richDataSets) d.underlying.save(directory / d.localPath)

    val plotFile = (directory / "results.m")
    plotFile.createFile(createParents = true, failIfExists = false)
    for (processor <- plotFile.outputProcessor; out = processor.asOutput)
      for (s <- statements) out.write(s.m + "\n")
  }
  
  def run() = {
    Process(
      "matlab -nodesktop -nosplash -r " + "results.m".takeWhile(_ != '.'),
      directory.fileOption, 
      "" -> ""
    ) #> (directory / "log.txt").fileOption.get run
  }

}

object Plot {
  private[this] var counter = -1
  private def next = { counter += 1; counter }
}