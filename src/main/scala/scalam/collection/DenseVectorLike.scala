package scalam.collection

import scala.collection.mutable.IndexedSeqLike
import breeze.linalg.DenseVector

trait DenseVectorLike[Elem, Repr] extends IndexedSeqLike[Elem, Repr]{
  def self: DenseVector[Elem]
  
  override def apply(index: Int): Elem = self.apply(index)
  override def update(index: Int, value: Elem): Unit = self.update(index, value)
  override def length: Int = self.length
}