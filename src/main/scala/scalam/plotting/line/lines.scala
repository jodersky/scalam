package scalam.plotting.line

import scalam.m.ast._
import scalam.plotting.StyleElement

trait Line extends StyleElement {
  val name = StringLiteral("LineStyle")
}

case object Solid extends Line {def expression = StringLiteral("-")}
case object Dashed extends Line {def expression = StringLiteral("--")}
case object Dotted extends Line {def expression = StringLiteral(":")}
case object DashDot extends Line {def expression = StringLiteral("-.")}
case object NoLine extends Line {def expression = StringLiteral("none")}