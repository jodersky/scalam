package scalam.plotting.scale

import scalam.m.ast._

trait Scale {def expression: Expression}
case object Lin extends Scale {def expression = StringLiteral("lin")}
case object Log extends Scale {def expression = StringLiteral("log")}