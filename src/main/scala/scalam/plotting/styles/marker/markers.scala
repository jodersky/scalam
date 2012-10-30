package scalam.plotting.styles.marker

import scalam.plotting.DataSet
import scalam.m.ast._
import scalam.plotting.styles.Style
import scalam.plotting.styles.StyleElement

trait Marker extends StyleElement {
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