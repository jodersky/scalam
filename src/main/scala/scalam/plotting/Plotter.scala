package scalam.plotting

import scalam.DataSet
import scalam.m._
import scalam.m.ast._
import scalam.plotting.styles._

trait Plotter {
  import Plotter._
  
  val pwd: scalax.file.Path
  
  lazy val interpreter = new MatlabInterpreter(pwd)
  
  //def plot(dataSets: Seq[DataSet], title: String, x: String, y: String, grid: Boolean = true, legend: Boolean = true)(implicit styles: Seq[Style[_]] = defaultStyles, fontSize: FontSize =  defaultFontSize): Plot =
    //new Plot(dataSets, title, x, y, grid, legend, styles = styles, fontSize = fontSize.fs)
  
  def plot(dataSets: Seq[DataSet], title: String, x: String, y: String, grid: Boolean = true, legend: Boolean = true)(implicit styles: Seq[Style[_]] = defaultStyles, fontSize: FontSize =  defaultFontSize) = {
    val p = new Plot(dataSets, title, x, y, grid, legend, styles = styles, fontSize = fontSize.fs)
    p.save()
    val s = Evaluate(Function(Identifier("run"), StringLiteral((p.directory / p.localPlotFile).path)))
    //val s = "run '" + (p.directory / p.localPlotFile).path + "'"
    println(s.m)
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