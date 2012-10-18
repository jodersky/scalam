package scalam.collection

import scala.collection.mutable.IndexedSeqOptimized
import breeze.linalg.DenseVector
import scala.collection.mutable.Builder
import scala.collection.generic.CanBuildFrom
import scala.collection.generic.SeqFactory
import scala.collection.generic.GenericCompanion
import scala.collection.mutable.IndexedSeqLike

class WrappedDenseVector[Elem: ClassManifest](val self: DenseVector[Elem]) extends IndexedSeq[Elem] with IndexedSeqLike[Elem, WrappedDenseVector[Elem]] {
  override def update(index: Int, value: Elem) = self.update(index, value)
  override def apply(index: Int) = self.apply(index)
  override def length = self.length
  override protected[this] def newBuilder: Builder[Elem, WrappedDenseVector[Elem]] = (new DenseVectorBuilder[Elem]) mapResult (new WrappedDenseVector(_))
}

object WrappedDenseVector {
  def newBuilder[Elem: ClassManifest] = (new DenseVectorBuilder[Elem]) mapResult (new WrappedDenseVector(_))

  implicit def canBuildFrom[T: ClassManifest] = new CanBuildFrom[WrappedDenseVector[_], T, WrappedDenseVector[T]] {
    def apply(from: WrappedDenseVector[_]): Builder[T, WrappedDenseVector[T]] = newBuilder
    def apply: Builder[T, WrappedDenseVector[T]] = newBuilder
  }
}