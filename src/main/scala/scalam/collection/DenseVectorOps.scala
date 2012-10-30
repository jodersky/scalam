package scalam.collection

import breeze.linalg.DenseVector
import scala.collection.mutable.ArrayBuffer
import scala.collection.generic.CanBuildFrom
import scala.collection.mutable.Builder

class DenseVectorOps[Elem: ClassManifest](override val repr: DenseVector[Elem]) extends DenseVectorLike[Elem, DenseVector[Elem]] {
  val self = repr
  def newBuilder = DenseVectorOps.newBuilder[Elem]
  
  override protected[this] def thisCollection = new WrappedDenseVector(self)
  override protected[this] def toCollection(repr: DenseVector[Elem]): WrappedDenseVector[Elem] = new WrappedDenseVector(repr)
  override def seq = thisCollection
}

object DenseVectorOps {
  def newBuilder[Elem: ClassManifest] = new ArrayBuffer[Elem]  mapResult (x => new DenseVector(x.toArray))
}
