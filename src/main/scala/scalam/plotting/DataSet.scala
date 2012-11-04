package scalam.plotting

import scalam.m.ast.Identifier
import scalax.file.Path
import breeze.linalg.{ Vector, DenseVector }
import scalam.io._
import scala.language.implicitConversions

case class DataSet(points: Seq[(Double, Double)], label: String, name: String) {

  lazy val (xs, ys) = points.unzip

  def save(path: Path) = {
    path.createFile(createParents = true, failIfExists = false)
    for (processor <- path.outputProcessor; out = processor.asOutput)
      for ((x, y) <- points) out.write(x + " " + y + "\n")
  }

}

object DataSet {

  def apply(points: Seq[(Double, Double)], label: String) = new DataSet(points, label, Identifier.makeValid(label))
  
  def apply(xs: Seq[Double], ys: Seq[Double], label: String) = new DataSet(xs zip ys, label, Identifier.makeValid(label))
  
  def apply(xs: Seq[Double], ys: Seq[Double], label: String, name: String) = new DataSet(xs zip ys, label, name)

  implicit def dataSetIsSaveable(ds: DataSet) = new Saveable {
    def save(out: scalax.file.Path) = for ((x, y) <- ds.points) yield out.write(x + " " + y + "\n")
  }

  implicit def dataSetIsLoadable = new Loadable[DataSet] {
    def load(in: scalax.file.Path) = new DataSet(Seq(), "", "")
  }

}

