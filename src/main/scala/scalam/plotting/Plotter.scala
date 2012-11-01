package scalam.plotting

import scalam.m._
import scalam.m.ast._
import scalam.plotting.styles._
import scalam.m.interpretation.MInterpreter
import scalam.m.interpretation.MatlabInterpreter
import scalax.file.Path

trait Plotter {
  import Plotter._
  
  val pwd: scalax.file.Path
  
  lazy val interpreter: MInterpreter = new MatlabInterpreter(pwd)
  
  def plot(dataSets: Seq[DataSet], title: String, x: String, y: String, grid: Boolean = true, legend: Boolean = true)(implicit styles: Seq[Style[StyleElement]] = defaultStyles, fontSize: FontSize =  defaultFontSize) = {
    val plot = new Plot(dataSets, title, x, y, grid, legend, styles = styles, fontSize = fontSize.fontSize)
    val path = Path(Identifier.makeValid(title))
    scalam.io.save(plot, path)
    val s = Function(Identifier("run"), StringLiteral((path / Plot.PlotFileName).path))
    println(s.line)
    interpreter.evaluate(s)
  }
  
  def exit() {
    interpreter.write("exit")
    interpreter.close()
  }

}

object Plotter {
  val defaultStyles = Seq(color.JET, Uniform(marker.Plus), Uniform(line.Solid))
  val defaultFontSize = FontSize(16)
}