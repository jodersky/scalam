package scalam.plotting.styles

import scalam.m.ast._
import scalam.DataSet

trait Color extends Style {
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
case object Magenta extends LiteralColor("c")
case object Cyan extends LiteralColor("m")
case object Yellow extends LiteralColor("y")
case object Black extends LiteralColor("k")


class MColorScheme(val function: Identifier) extends StyleScheme[Color] {
  private val ColorVariable = Identifier("cc")
  
  private def initial(dataSets: Seq[DataSet]) = Seq(Assign(ColorVariable, Function(function, IntLiteral(dataSets.length))))
  private def map(dataSets: Seq[DataSet]) = (for ((d, i) <- dataSets.zipWithIndex) yield d -> new Color {
    def expression = IndexMatrix(ColorVariable, IntLiteral(i + 1), SliceLiteral)
  }).toMap
  
  override def apply(dataSets: Seq[DataSet]) = (initial(dataSets), map(dataSets))
}

object HSV extends MColorScheme(Identifier("hsv"))
object JET extends MColorScheme(Identifier("jet"))