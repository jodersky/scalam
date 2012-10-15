package scalam.plotting.styles

import scalam.DataSet
import scalam.m.ast._

trait Marker extends Style {
  def name = StringLiteral("Marker")
}
case object Plus extends Marker { def expression = StringLiteral("+") }
case object Circle extends Marker { def expression = StringLiteral("o") }
case object Asterisk extends Marker { def expression = StringLiteral("*") }
case object Point extends Marker { def expression = StringLiteral(".") }
case object Cross extends Marker { def expression = StringLiteral("x") }
case object Square extends Marker { def expression = StringLiteral("s") }
case object Diamond extends Marker { def expression = StringLiteral("d") }
case object UpTriangle extends Marker { def expression = StringLiteral("^") }
case object DownTriangle extends Marker { def expression = StringLiteral("v") }
case object RightTriangle extends Marker { def expression = StringLiteral(">") }
case object LeftTriangle extends Marker { def expression = StringLiteral("<") }
case object Pentagram extends Marker { def expression = StringLiteral("p") }
case object Hexagram extends Marker { def expression = StringLiteral("h") }
case object NoMarker extends Marker { def expression = StringLiteral("none") }

object AllMarkerScheme extends StyleScheme[Marker] {

  val markers = List(Plus, Circle, Asterisk, Point, Cross, Square, Diamond, UpTriangle, DownTriangle, RightTriangle, LeftTriangle, Pentagram, Hexagram)
  private def map(dataSets: Seq[DataSet]): Map[DataSet, Marker] = dataSets.zipWithIndex.map{
    case (d, i) => d -> markers(i % (markers.length-1))    
  }.toMap
  
  override def apply(dataSets: Seq[DataSet]) = (Seq.empty[Statement], map(dataSets))

}