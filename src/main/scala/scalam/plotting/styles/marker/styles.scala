package scalam.plotting.styles.marker

import scalam.DataSet
import scalam.m.ast._
import scalam.plotting.styles.Style
import scalam.plotting.styles.StyleElement

object AllMarkerStyle extends Style[Marker] {

  val markers = List(Plus, Circle, Asterisk, Point, Cross, Square, Diamond, UpTriangle, DownTriangle, RightTriangle, LeftTriangle, Pentagram, Hexagram)
  private def map(dataSets: Iterable[DataSet]): Map[DataSet, Marker] = dataSets.zipWithIndex.map{
    case (d, i) => d -> markers(i % (markers.length-1))    
  }.toMap
  
  def apply(dataSets: Seq[DataSet]) = (Seq.empty[Statement], map(dataSets))

}