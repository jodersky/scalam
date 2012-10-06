package scalam.plotting

import scalam.DataSet
import scalam.m.ast._

trait Color {
  def mColor: Expression
}
class RGB(r: Double, g: Double, b: Double) extends Color {
  def mColor = ArrayLiteral(DoubleLiteral(r), DoubleLiteral(g), DoubleLiteral(b)) 
}
case object Green extends RGB(0, 1, 0)
case object Red extends RGB(1, 0, 0)
case object Blue extends RGB(0, 0, 1)
case object Magenta extends RGB(1, 0, 1)
case object Cyan extends RGB(0, 1, 1)
case object Yellow extends RGB(1, 1, 0)
case object Black extends RGB(0, 0, 0)

trait ColorScheme { self =>
  def color(dataSet: DataSet): Color
  
  def except(special: Map[DataSet, Color]) = new ColorScheme {
    private val exceptions: Map[DataSet, Color] = special 
    def color(dataSet: DataSet) = {
      exceptions.getOrElse(dataSet, self.color(dataSet))
    }
  }
}

trait ColorSchemeFactory[C <: ColorScheme] { self =>
  
  def apply(dataSets: Seq[DataSet]): C
  
  def except(special: Map[DataSet, Color]) = new ColorSchemeFactory[ColorScheme] {
    def apply(dataSets: Seq[DataSet]) = self.apply(dataSets) except special
  }
}

trait MColorScheme extends ColorScheme{
  protected val function: Identifier
  protected val dataSets: Seq[DataSet]
  private val indices: Map[DataSet, Int] = dataSets.zipWithIndex.toMap
  
  private val ColorVariable = Identifier("cc")
  
  def initial = Assign(ColorVariable, Call(function, IntLiteral(dataSets.length)))
  
  def color(dataSet: DataSet) = new Color{
    def mColor = IndexMatrix(ColorVariable, IntLiteral(indices(dataSet) + 1), SliceLiteral) 
  }
}

class HSV(protected val dataSets: Seq[DataSet]) extends MColorScheme {val function = Identifier("hsv")}
object HSV extends ColorSchemeFactory[HSV] {def apply(dataSets: Seq[DataSet]) = new HSV(dataSets)}

class JET(protected val dataSets: Seq[DataSet]) extends MColorScheme {val function = Identifier("jet")}
object JET extends ColorSchemeFactory[JET] {def apply(dataSets: Seq[DataSet]) = new JET(dataSets)}

case class Uniform(color: Color) extends ColorScheme {
  def color(dataSet: DataSet) = color
}