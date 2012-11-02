package scalam.collection

import scala.collection.mutable.IndexedSeq
import breeze.linalg.DenseVector
import scala.collection.mutable.Builder
import scala.collection.generic.CanBuildFrom
import scala.collection.mutable.ArrayBuffer
import scala.reflect.ClassTag

/**
 * This class serves as a wrapper augmenting `breeze.linalg.DenseVector`s with all the operations
 * found in indexed sequences.
 *
 * The difference between this class and `DenseVectorOps` is that calling transformer
 * methods such as `filter` and `map` will yield an object of type `WrappedDenseVector`
 * rather than a `DenseVector`.
 *
 * @see `scala.collection.mutable.WrappedArray`
 * 
 * @tparam Elem type of the elements contained in this WrappedDenseVector.
 *
 * @param self a dense vector contained within this wrapped dense vector
 *
 * @define Coll `DenseVector`
 * @define coll dense vector
 */
class WrappedDenseVector[Elem: ClassTag](val self: DenseVector[Elem]) extends IndexedSeq[Elem] with DenseVectorLike[Elem, WrappedDenseVector[Elem]] {
  override protected[this] def newBuilder: Builder[Elem, WrappedDenseVector[Elem]] = WrappedDenseVector.newBuilder[Elem]
}

/**
 * A companion object for wrapped dense vectors.
 */
object WrappedDenseVector {
  def newBuilder[Elem: ClassTag] = (new ArrayBuffer[Elem]) mapResult (x => new WrappedDenseVector(DenseVector(x.toArray)))

  implicit def canBuildFrom[T: ClassTag] = new CanBuildFrom[WrappedDenseVector[_], T, WrappedDenseVector[T]] {
    def apply(from: WrappedDenseVector[_]): Builder[T, WrappedDenseVector[T]] = newBuilder[T]
    def apply: Builder[T, WrappedDenseVector[T]] = newBuilder[T]
  }
}