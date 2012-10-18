import breeze.linalg.DenseVector
import scalam.collection._

package object scalam extends LowPriorityImplicits{
  
  implicit def denseVector2Ops[A: ClassManifest](v: DenseVector[A]) = new DenseVectorOps(v)

}