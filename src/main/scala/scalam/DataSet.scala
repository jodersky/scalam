package scalam

import scalax.file.Path
import breeze.linalg.{Vector, DenseVector}

case class DataSet(val points: Seq[(Double, Double)], val name: String) {
  
  def save(path: Path) = {
    path.createFile(createParents = true, failIfExists = false)
    for (processor <- path.outputProcessor; out = processor.asOutput)
      for ((x, y) <- points) out.write(x + " " + y + "\n")
  }
  
  lazy val (xs, ys) = points.unzip match {
    case (x, y) => (DenseVector(x: _*), DenseVector(y: _*))
  }
  
}

object DataSet {
  
  def apply(x: Vector[Double], y: Vector[Double], name: String = "") = new DataSet(x.valuesIterator.toSeq zip y.valuesIterator.toSeq, name)
   
}