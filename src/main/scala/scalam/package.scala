import breeze.linalg.DenseVector
import breeze.linalg.DenseMatrix
import scalam.collection._
import scala.collection.generic.CanBuildFrom
import scala.collection.mutable.Builder
import scalam.io.{ Loadable, Saveable }

/**
 * A number of commonly applied implicit conversions are defined here, and
 * in the parent type [[scalam.LowPriorityImplicits]]. Implicit conversions
 * are provided for the integration of external collection-like structures into
 * the standard Scala collections framework.
 */
package object scalam extends LowPriorityImplicits {

  /**
   * Augments a given dense vector to a DenseVectorOps object, thereby providing it with all the methods found in sequences.
   * @see scalam.collection.DenseVectorOps
   */
  implicit def denseVector2Ops[A: ClassManifest](v: DenseVector[A]) = new DenseVectorOps(v)

  /**
   * Enables new dense vectors to be built from existing dense vectors. This implicit is used by [[scalam.collection.DenseVectorOps]].
   */
  implicit def denseVectorCanBuildFrom[A: ClassManifest]: CanBuildFrom[DenseVector[_], A, DenseVector[A]] =
    new CanBuildFrom[DenseVector[_], A, DenseVector[A]] {
      def apply(from: DenseVector[_]): Builder[A, DenseVector[A]] = apply()
      def apply(): Builder[A, DenseVector[A]] = collection.DenseVectorOps.newBuilder[A]
    }

  def denseMatrixIsLoadable[A: ClassManifest](converter: String => A): Loadable[DenseMatrix[A]] = new Loadable[DenseMatrix[A]] {
    def load(in: scalax.io.Input) = {
      val lines: Array[String] = in.lines().dropWhile(_.isEmpty).toArray
      val separator = "\\s|,"
      val elements: Array[Array[String]] = lines.map(_.split(separator))
      require(elements.forall(_.length == elements(0).length), "Cannot load non-rectangular matrix. Check your data file.")

      val linear = elements.transpose.flatten
      new DenseMatrix(elements.length, linear.map(converter(_)))
    }
  }

  implicit def intDenseIsLoadable = denseMatrixIsLoadable[Int](_.toInt)
  implicit def doubleDenseIsLoadable = denseMatrixIsLoadable[Double](_.toDouble)
  implicit def floatDenseIsLoadable = denseMatrixIsLoadable[Float](_.toFloat)
  implicit def byteDenseIsLoadable = denseMatrixIsLoadable[Byte](_.toByte)
  implicit def longDenseIsLoadable = denseMatrixIsLoadable[Long](_.toLong)
  implicit def booleanDenseIsLoadable = denseMatrixIsLoadable[Boolean](_.toBoolean)

  implicit def denseMatrixIsSaveable = (m: DenseMatrix[_]) => new Saveable {
    def save(out: scalax.io.Output) = {
      for (i <- 0 until m.rows) m(i, ::).valuesIterator.mkString("", " ", "\n")
    }
  }

}