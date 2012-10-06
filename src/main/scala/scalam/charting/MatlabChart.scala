package scalam.charting

import scalam._
import scalax.file.Path
import scala.sys.process._

class MatlabChart(
    val dataSets: Seq[DataSet],
    title: String,
    xAxis: String,
    yAxis: String,
    grid: Boolean = true,
    fontSize: Int = 10,
    legendFontSize: Int = 10,
    colorScheme: ColorScheme = Uniform(),
    name: String = "chart" + MatlabChart.next.toString
){
  
  val directory = Path(name)
  val dataDirectory = Path("data")
  val mFile = Path("chart.m")

  private case class MatlabDataSet(underlying: DataSet, path: Path, variableName: String)

  private lazy val matlabDataSets: Seq[MatlabDataSet] = dataSets.zipWithIndex map {
    case (dataSet, index) =>
      MatlabDataSet(dataSet, dataDirectory / (index.toString), "data" + index)
  }

  lazy val mScriptLines: Seq[String] =
    (for (m <- matlabDataSets) yield m.variableName + " = load('" + m.path.path + "');") ++
    Seq("legendText=cell(1," + matlabDataSets.length + ");", "fh=figure;", "hold('on');") ++
    (colorScheme match {
      case m: MatlabColorScheme => Seq(m.initial)
      case _ => Seq()
    }) ++
    (for ((m, i) <- matlabDataSets.zipWithIndex) yield Seq(
        "plot(" + m.variableName + "(:,1)" + ", " + m.variableName + "(:,2), 'color', " + colorScheme.color(m.underlying) + ");",
        "legendText{" + (i+1) + "} = '" + m.underlying.name + "';"
    )).flatten ++
    Seq("lg=legend(legendText{:});",
        "set(gca, 'fontsize'," + fontSize + ");",
        "set(lg, 'fontsize'," + legendFontSize + ");",
        "grid('" + (if (grid) "on" else "off") + "');",
        "title('" + title + "');",
        "xlabel('" + xAxis + "');",
        "ylabel('" + yAxis + "');",
        "waitfor(fh);")
        
        
  def save() = {
    for (m <- matlabDataSets) m.underlying.save(directory / m.path)
    val mFromHere = (directory / mFile)
    mFromHere.createFile(createParents = true, failIfExists = false)
    for (processor <- mFromHere.outputProcessor; out = processor.asOutput)
      for (line <- mScriptLines) out.write(line + "\n")
  }

  def run() = {
    Process(
      "matlab -nodesktop -nosplash -r " + mFile.name.takeWhile(_ != '.'),
      directory.fileOption, 
      "" -> ""
    ) #> (directory / "log.txt").fileOption.get run
  }

}

object MatlabChart {
  private var counter = -1;
  private def next = { counter += 1; counter }

  val test = Seq(
    new DataSet(Seq((0.0, 1.0), (1.0, 1.0), (2.0, 1.0), (3.0, 0.0), (4.0, 1.0), (5.0, 1.0)), "temperature"),
    new DataSet(Seq((0.0, 0.0), (1.0, 1.0), (2.0, 4.0), (3.0, 9.0)), """\alpha"""))
    
    val testChart = new MatlabChart(test, "title", "x [\\sigma \\epsilon]", "\\vec{y} [\\frac{1}{2}]", colorScheme = new HSV(test))
    
}