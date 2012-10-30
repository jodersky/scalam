package scalam.collection

import scala.collection.mutable.IndexedSeqLike
import breeze.linalg.DenseVector

/** A common supertrait of `DenseVectorOps` and `WrappedDenseVector` that factors out most
* operations on dense vectors and wrapped dense vectors.
*
* @tparam Elem type of the elements contained in the dense vector like object.
* @tparam Repr the type of the actual collection containing the elements.
*
* @define Coll `DenseVectorLike`
*/
trait DenseVectorLike[Elem, Repr] extends IndexedSeqLike[Elem, Repr]{
  
  /** Underlying dense vector. */
  def self: DenseVector[Elem]
  
  override def apply(index: Int): Elem = self.apply(index)
  override def update(index: Int, value: Elem): Unit = self.update(index, value)
  override def length: Int = self.length
}