package scalam

import scalax.file.Path
import breeze.linalg._

package object io {
  
  def load(path: Path, separator: String = "\\s"): DenseMatrix[Double] = {
    val lines = path.lines().dropWhile(_.isEmpty).toArray
    val elements: Array[Array[String]] = lines map (_.split(separator))
    require(elements.forall(_.length == elements(0).length), "Cannot load non-rectangular matrix. Check your data file.")
    
    val rows: Array[Array[Double]] = elements.map(_.map(_.toDouble))
    DenseMatrix(rows: _*)
  }
  
  def save(path: Path, m: DenseMatrix[Double]): Unit = {
    path.createFile(createParents = true, failIfExists = false)
    for (processor <- path.outputProcessor; out = processor.asOutput)
      for (i <- 0 until m.rows) m(i, ::).valuesIterator.mkString(""," ","\n")
  }
  
}