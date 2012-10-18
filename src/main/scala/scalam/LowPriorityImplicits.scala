package scalam

import breeze.linalg.DenseVector
import scalam.collection._

object LowPriorityImplicits {
  
  implicit def wrapDenseVector[A: ClassManifest](v: DenseVector[A]) = new WrappedDenseVector(v)
  implicit def unwrapDenseVector[A: ClassManifest](w: WrappedDenseVector[A]) = w.self
  
}