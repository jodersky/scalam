package scalam.plotting.styles

import scalam.plotting.DataSet
import scalam.m.ast._


trait StyleElement {
  //command line option
  def name: StringLiteral
  //expression
  def expression: Expression
}

trait Style[+S <: StyleElement] {  
  def apply(dataSets: Seq[DataSet]): Tuple2[Seq[Statement], DataSet => S]
}

case class Uniform[S <: StyleElement](element: S) extends Style[S] {
  override def apply(dataSets: Seq[DataSet]) = (Seq.empty[Statement], (d: DataSet) => element)
}