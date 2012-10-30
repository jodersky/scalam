package scalam.plotting

import scalax.file.Path
import breeze.linalg.{ Vector, DenseVector }

case class DataSet(points: Seq[(Double, Double)], label: String) {
  val name = label
  
  lazy val (xs, ys) = points.unzip
  def save(path: Path) = {
    path.createFile(createParents = true, failIfExists = false)
    for (processor <- path.outputProcessor; out = processor.asOutput)
      for ((x, y) <- points) out.write(x + " " + y + "\n")
  }

}

