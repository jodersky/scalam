package scalam

import scalax.file.Path
import breeze.linalg.DenseVector

class DataSet(val points: Seq[(Double, Double)], val name: String) {
  
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
  
  def apply(x: DenseVector[Double], y: DenseVector[Double], name: String = "") = new DataSet(x.data zip y.data, name)
  
}