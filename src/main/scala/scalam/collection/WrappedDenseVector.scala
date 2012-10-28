package scalam.collection

import scala.collection.mutable.IndexedSeq
import breeze.linalg.DenseVector
import scala.collection.mutable.Builder
import scala.collection.generic.CanBuildFrom
import scala.collection.mutable.ArrayBuffer

class WrappedDenseVector[Elem: ClassManifest](val self: DenseVector[Elem]) extends IndexedSeq[Elem] with DenseVectorLike[Elem, WrappedDenseVector[Elem]] {
  override protected[this] def newBuilder: Builder[Elem, WrappedDenseVector[Elem]] = WrappedDenseVector.newBuilder[Elem]
}

object WrappedDenseVector {
  def newBuilder[Elem: ClassManifest] = (new ArrayBuffer[Elem]) mapResult (x => new WrappedDenseVector(DenseVector(x.toArray)))

  implicit def canBuildFrom[T: ClassManifest] = new CanBuildFrom[WrappedDenseVector[_], T, WrappedDenseVector[T]] {
    def apply(from: WrappedDenseVector[_]): Builder[T, WrappedDenseVector[T]] = newBuilder[T]
    def apply: Builder[T, WrappedDenseVector[T]] = newBuilder[T]
  }
}