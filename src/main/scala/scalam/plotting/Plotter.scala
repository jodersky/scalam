package scalam.plotting

import scalam.m._
import scalam.m.ast._
import scalam.m.interpretation.MInterpreter
import scalax.file.Path
import scalam.plotting.scale.Scale
import scalam.plotting.scale.Lin

trait Plotter { self: MInterpreter =>
  import Plotter._
  
  def plot(dataSets: Seq[DataSet], title: String, x: String, y: String, scales: (Scale, Scale) = (Lin, Lin), grid: Boolean = true, legend: Boolean = true)(implicit styles: Seq[Style[StyleElement]] = defaultStyles, fontSize: FontSize =  defaultFontSize) = {
    val plot = new Plot(dataSets, title, Axis(x, scales._1), Axis(y, scales._2), grid, legend, styles = styles, fontSize = fontSize.fontSize)
    val path = Path(Identifier.makeValid(title))
    scalam.io.save(plot, path)
    val s = Function(Identifier("run"), StringLiteral((path / Plot.PlotFileName).path))
    self.evaluate(s)
  }
  
}

object Plotter {
  val defaultStyles = Seq(color.JET, Uniform(marker.Plus), Uniform(line.Solid))
  val defaultFontSize = FontSize(16)
}