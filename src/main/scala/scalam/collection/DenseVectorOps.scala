package scalam.collection

import breeze.linalg.DenseVector
import scala.collection.mutable.ArrayBuffer
import scala.collection.generic.CanBuildFrom
import scala.collection.mutable.Builder

/* TODO find solution to remove class manifest */
class DenseVectorOps[Elem: ClassManifest](override val repr: DenseVector[Elem]) extends DenseVectorLike[Elem, DenseVector[Elem]] {
  val self = repr
  def newBuilder = DenseVectorOps.newBuilder[Elem]
  
  override protected[this] def thisCollection = new WrappedDenseVector(self)
  override protected[this] def toCollection(repr: DenseVector[Elem]): WrappedDenseVector[Elem] = new WrappedDenseVector(repr)
  override def seq = thisCollection
}

object DenseVectorOps {
  def newBuilder[Elem: ClassManifest] = new ArrayBuffer[Elem]  mapResult (x => new DenseVector(x.toArray))
  
  implicit def canBuildFrom[T: ClassManifest] = new CanBuildFrom[DenseVector[_], T, DenseVector[T]] {
    def apply(from: DenseVector[_]): Builder[T, DenseVector[T]] = newBuilder[T]
    def apply: Builder[T, DenseVector[T]] = newBuilder[T]
  }
  /*
   implicit def denseVectorBuildFrom[A: ClassManifest]: CanBuildFrom[DenseVector[_], A, DenseVector[A]] =
    new CanBuildFrom[DenseVector[_], A, DenseVector[A]] {
      def apply(from: DenseVector[_]): Builder[A, DenseVector[A]] = apply()
      def apply(): Builder[A, DenseVector[A]] = collection.DenseVectorOps.newBuilder[A]
    }
    */
}
