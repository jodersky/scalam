package scalam.plotting.styles

import scalam.DataSet
import scalam.m.ast._


trait Style {
  //command line option
  def name: StringLiteral
  //expression
  def expression: Expression
}


trait StyleScheme[+S <: Style] {  
  def apply(dataSets: Seq[DataSet]): Tuple2[Seq[Statement], DataSet => S]
}

case class Uniform[S <: Style](element: S) extends StyleScheme[S] {
  override def apply(dataSets: Seq[DataSet]) = (Seq.empty[Statement], (d: DataSet) => element)
}