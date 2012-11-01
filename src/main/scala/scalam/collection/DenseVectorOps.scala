package scalam.collection

import breeze.linalg.DenseVector
import scala.collection.mutable.ArrayBuffer
import scala.collection.generic.CanBuildFrom
import scala.collection.mutable.Builder
import scala.reflect.ClassTag

/**
 * This class serves as a wrapper for `breeze.linalg.DenseVector`s with all the operations found in
 * indexed sequences. Where needed, instances of DenseVectors are implicitly converted
 * into this class.
 *
 * The difference between this class and `WrappedDenseVector` is that calling transformer
 * methods such as `filter` and `map` will yield a `DenseVector`, whereas a `WrappedDenseVector`
 * will remain a `WrappedDenseVector`.
 *
 * @see [[scala.collection.mutable.ArrayOps]]
 *
 * @tparam Elem type of the elements contained in this DenseVector.
 *
 * @define Coll `DenseVector`
 * @define orderDependent
 * @define orderDependentFold
 * @define mayNotTerminateInf
 * @define willNotTerminateInf
 */
class DenseVectorOps[Elem: ClassTag](override val repr: DenseVector[Elem]) extends DenseVectorLike[Elem, DenseVector[Elem]] {
  val self = repr
  def newBuilder = DenseVectorOps.newBuilder[Elem]

  override protected[this] def thisCollection = new WrappedDenseVector(self)
  override protected[this] def toCollection(repr: DenseVector[Elem]): WrappedDenseVector[Elem] = new WrappedDenseVector(repr)
  override def seq = thisCollection
}

/**
 * A companion object for DenseVectorsOps.
 */
object DenseVectorOps {
  def newBuilder[Elem: ClassTag] = new ArrayBuffer[Elem] mapResult (x => new DenseVector(x.toArray))
}
