package scalam.plotting

import scalam.m.ast._

trait StyleElement {
  //command line option
  def name: Expression
  //expression
  def expression: Expression
}

trait Style[+S <: StyleElement] {
  def apply(dataSets: Seq[DataSet]): (Seq[Root], DataSet => S)
}

case class Uniform[S <: StyleElement](element: S) extends Style[S] {
  override def apply(dataSets: Seq[DataSet]) = (Seq.empty[Statement], (d: DataSet) => element)
}