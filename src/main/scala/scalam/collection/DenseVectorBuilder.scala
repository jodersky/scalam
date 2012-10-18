package scalam.collection

import scala.collection.mutable.Builder
import scala.collection.mutable.ArrayBuffer
import breeze.linalg.DenseVector

class DenseVectorBuilder[Elem: ClassManifest] extends Builder[Elem, DenseVector[Elem]] {
  private val buffer = new ArrayBuffer[Elem]
  
  override def +=(elem: Elem) = {buffer += elem; this}
  override def clear() = buffer.clear
  override def result() = {
   val a = buffer.mapResult(_.toArray).result
   DenseVector(a)
  }
} 