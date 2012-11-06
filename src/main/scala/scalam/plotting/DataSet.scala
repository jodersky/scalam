package scalam.plotting

import scalam.m.ast.Identifier
import scalax.file.Path
import breeze.linalg.{ Vector, DenseVector }
import scalam.io._
import scala.language.implicitConversions

case class DataSet(points: Seq[(Double, Double)], label: String, name: String) {

  lazy val (xs, ys) = points.unzip

}

object DataSet {

  def apply(points: Seq[(Double, Double)], label: String) = new DataSet(points, label, Identifier.makeValid(label))
  
  def apply(xs: Seq[Double], ys: Seq[Double], label: String) = new DataSet(xs zip ys, label, Identifier.makeValid(label))
  
  def apply(xs: Seq[Double], ys: Seq[Double], label: String, name: String) = new DataSet(xs zip ys, label, name)

  implicit def dataSetIsSaveable(ds: DataSet) = new Saveable {
    def save(out: scalax.file.Path) = {
      val data = ds.points.map{case (x, y) => x + " " + y}.mkString("\n")
      out.write(data)
    }
  }

}

