package scalam.charting

import scalam.DataSet

trait ColorScheme {
  def color(dataSet: DataSet): String
}

trait ColorSchemeFactory[C <: ColorScheme] {
  def create(dataSets: Seq[DataSet]): C
}

trait MatlabColorScheme extends ColorScheme {
  private final val ColorVariableName = "cc"
  
  protected val dataSets: Seq[DataSet]
  protected val function: String
  private val indices: Map[DataSet, Int] = dataSets.zipWithIndex.toMap
  
  def initial = {
    ColorVariableName + " = " + function + "(" + dataSets.length + ");"
  }
  
  def color(dataSet: DataSet) = ColorVariableName + "(" + (indices(dataSet) + 1) + ",:)"
}

case class Uniform(rgb: (Double, Double, Double) = (0, 0, 0)) extends ColorScheme {
  private val (r, g, b) = rgb
  def color(dataSet: DataSet) = "[" + r + "," + g + "," + b + "]"
}

class HSV(protected val dataSets: Seq[DataSet]) extends MatlabColorScheme {val function = "hsv"}
object HSV extends ColorSchemeFactory[HSV] {def create(dataSets: Seq[DataSet]) = new HSV(dataSets)}
class JET(protected val dataSets: Seq[DataSet]) extends MatlabColorScheme {val function = "hsv"}
object JET extends ColorSchemeFactory[JET] {def create(dataSets: Seq[DataSet]) = new JET(dataSets)}

trait Special extends ColorScheme {
  val specialDataSet: DataSet
  val rgb: (Double, Double, Double)
  
  private val (r, g, b) = rgb
  
  abstract override def color(dataSet: DataSet): String =
    if (dataSet == specialDataSet) "[" + r + "," + g + "," + b + "]" 
    else super.color(dataSet)
}

//abstract class Special(val specialDataSet: DataSet, val rgb: (Double, Double, Double)) extends SpecialImpl


