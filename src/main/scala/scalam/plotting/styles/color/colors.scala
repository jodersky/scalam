package scalam.plotting.styles.color

import scalam.m.ast._
import scalam.DataSet
import scalam.plotting.styles.Style
import scalam.plotting.styles.StyleElement

trait Color extends StyleElement {
  def name = StringLiteral("Color")
}

class RGB(r: Double, g: Double, b: Double) extends Color {
  def expression = ArrayLiteral(DoubleLiteral(r), DoubleLiteral(g), DoubleLiteral(b)) 
}

class LiteralColor(value: String) extends Color {
  def expression = StringLiteral(value)
}

case object Red extends LiteralColor("r")
case object Green extends LiteralColor("g")
case object Blue extends LiteralColor("b")
case object Magenta extends LiteralColor("m")
case object Cyan extends LiteralColor("c")
case object Yellow extends LiteralColor("y")
case object Black extends LiteralColor("k")