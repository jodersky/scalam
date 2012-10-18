package scalam.plotting.styles.color

import scalam.m.ast._
import scalam.DataSet
import scalam.plotting.styles.Style
import scalam.plotting.styles.StyleElement

class MColorStyle(val function: Identifier) extends Style[Color] {
  private val ColorVariable = Identifier("cc")
  
  private def initial(dataSets: Seq[DataSet]) = Seq(Assign(ColorVariable, Function(function, IntLiteral(dataSets.length))))
  private def map(dataSets: Iterable[DataSet]) = (for ((d, i) <- dataSets.zipWithIndex) yield d -> new Color {
    def expression = IndexMatrix(ColorVariable, IntLiteral(i + 1), SliceLiteral)
  }).toMap
  
  override def apply(dataSets: Seq[DataSet]) = (initial(dataSets), map(dataSets))
}

object HSV extends MColorStyle(Identifier("hsv"))
object JET extends MColorStyle(Identifier("jet"))