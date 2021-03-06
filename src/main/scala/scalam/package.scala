import breeze.linalg.DenseVector
import breeze.linalg.DenseMatrix
import scalam.collection._
import scala.collection.generic.CanBuildFrom
import scala.collection.mutable.Builder
import scala.reflect.ClassTag
import scalam.io.{ Loadable, Saveable }
import scala.language.implicitConversions

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
  implicit def denseVector2Ops[A: ClassTag](v: DenseVector[A]) = new DenseVectorOps(v)

  /**
   * Enables new dense vectors to be built from existing dense vectors. This implicit is used by [[scalam.collection.DenseVectorOps]].
   */
  implicit def denseVectorCanBuildFrom[A: ClassTag]: CanBuildFrom[DenseVector[_], A, DenseVector[A]] =
    new CanBuildFrom[DenseVector[_], A, DenseVector[A]] {
      def apply(from: DenseVector[_]): Builder[A, DenseVector[A]] = apply()
      def apply(): Builder[A, DenseVector[A]] = collection.DenseVectorOps.newBuilder[A]
    }

  def denseMatrixIsLoadable[A: ClassTag](converter: String => A): Loadable[DenseMatrix[A]] = new Loadable[DenseMatrix[A]] {
    def load(in: scalax.file.Path) = {
      val lines: Array[String] = in.lines().dropWhile(_.isEmpty).toArray
      val separator = "\\s+|,"
      val elements: Array[Array[String]] = lines.map(_.trim.split(separator))
      require(elements.forall(_.length == elements(0).length), "Cannot load non-rectangular matrix. Check your data file: " + in.path)

      val linear = elements.transpose.flatten
      new DenseMatrix(elements.length, linear.map(converter(_)))
    }
  }

  implicit def intDenseMatrixIsLoadable = denseMatrixIsLoadable[Int](_.toInt)
  implicit def doubleDenseMatrixIsLoadable = denseMatrixIsLoadable[Double](_.toDouble)
  implicit def floatDenseMatrixIsLoadable = denseMatrixIsLoadable[Float](_.toFloat)
  implicit def byteDenseMatrixIsLoadable = denseMatrixIsLoadable[Byte](_.toByte)
  implicit def longDenseMatrixIsLoadable = denseMatrixIsLoadable[Long](_.toLong)
  implicit def booleanDenseMatrixIsLoadable = denseMatrixIsLoadable[Boolean](_.toBoolean)

  implicit def denseMatrixIsSaveable(m: DenseMatrix[_]) = new Saveable {
    def save(out: scalax.file.Path) = {
      val data = for (i <- 0 until m.rows) yield m(i, ::).valuesIterator.mkString(" ")
      out.write(data.mkString("\n"))
    }
  }
}