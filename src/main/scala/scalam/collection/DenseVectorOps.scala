package scalam.collection

import scala.collection.mutable.IndexedSeqOptimized
import breeze.linalg.DenseVector

/* TODO find solution to remove class manifest */
class DenseVectorOps[Elem: ClassManifest](self: DenseVector[Elem]) extends IndexedSeqOptimized/*Like*/[Elem, DenseVector[Elem]] {
  override def newBuilder = new DenseVectorBuilder[Elem]
  
  override def apply(index: Int) = self.apply(index)
  override def update(index: Int, value: Elem) = self.update(index, value)
  override def length = self.length
  
  override def seq = this
}

