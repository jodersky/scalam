package scalam.plotting.styles

import scalam.m.ast._

trait Line extends Style {
  def name = StringLiteral("LineStyle")
}

case object Solid extends Line {def expression = StringLiteral("-")}
case object Dashed extends Line {def expression = StringLiteral("--")}
case object Dotted extends Line {def expression = StringLiteral(":")}
case object DashDot extends Line {def expression = StringLiteral("-.")}
case object NoLine extends Line {def expression = StringLiteral("none")}