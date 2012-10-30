import breeze.linalg.DenseVector
import scalam.collection._
import scala.collection.generic.CanBuildFrom
import scala.collection.mutable.Builder

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

}