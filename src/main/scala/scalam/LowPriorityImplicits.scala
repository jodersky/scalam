package scalam

import breeze.linalg.DenseVector
import scalam.collection._

/** Defines implicit conversions with a lower priority than those found in [[scalam]]'s package object.*/
trait LowPriorityImplicits {

  /**
   * Wraps the given dense vector to a sequence.
   * @see scalam.collection.WrappedDenseVector
   */
  implicit def wrapDenseVector[A: ClassManifest](v: DenseVector[A]) = new WrappedDenseVector(v)

  /**
   * Unwraps the given wrapped dense vector to a normal dense vector.
   * @see scalam.collection.WrappedDenseVector
   */
  implicit def unwrapDenseVector[A: ClassManifest](w: WrappedDenseVector[A]) = w.self

}