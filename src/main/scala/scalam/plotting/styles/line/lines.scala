package scalam.plotting.styles.line

import scalam.m.ast._
import scalam.plotting.styles.StyleElement

trait Line extends StyleElement {
  def name = StringLiteral("LineStyle")
}

case object Solid extends Line {def expression = StringLiteral("-")}
case object Dashed extends Line {def expression = StringLiteral("--")}
case object Dotted extends Line {def expression = StringLiteral(":")}
case object DashDot extends Line {def expression = StringLiteral("-.")}
case object NoLine extends Line {def expression = StringLiteral("none")}