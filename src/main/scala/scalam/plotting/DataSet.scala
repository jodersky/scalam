package scalam.plotting

import scalam.m.ast.Identifier
import scalax.file.Path
import breeze.linalg.{ Vector, DenseVector }

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
  
}

