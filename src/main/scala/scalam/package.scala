import breeze.linalg.DenseVector
import scalam.collection._
import scala.collection.generic.CanBuildFrom
import scala.collection.mutable.Builder

package object scalam extends LowPriorityImplicits {
  
  implicit def denseVector2Ops[A: ClassManifest](v: DenseVector[A]) = new DenseVectorOps(v)

  implicit def denseVectorBuildFrom[A: ClassManifest]: CanBuildFrom[DenseVector[_], A, DenseVector[A]] =
    new CanBuildFrom[DenseVector[_], A, DenseVector[A]] {
      def apply(from: DenseVector[_]): Builder[A, DenseVector[A]] = apply()
      def apply(): Builder[A, DenseVector[A]] = collection.DenseVectorOps.newBuilder[A]
    }
  
}