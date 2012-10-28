package scalam

import scala.collection.SeqLike
import scala.collection.generic.CanBuildFrom
package object math {
  
  def smooth[Elem, Coll[Elem]](width: Int, passes: Int, collection: Coll[Elem])(implicit fractional: Fractional[Elem], cbf: CanBuildFrom[Coll[Elem], Elem, Coll[Elem]], view: Coll[Elem] => Seq[Elem]): Coll[Elem] = {
    def average(xs: Seq[Elem]): Elem = {
      import fractional._
      xs.sum / fromInt(xs.length)
    }
    
    def smoothOnce(width: Int, collection: Coll[Elem]) = {
      val b = cbf(collection)
      collection.sliding(width).foreach(neighbours => b += average(neighbours))
      b.result()
      
    }
    
    if (passes <= 0) collection
    else smooth(width, passes - 1, smoothOnce(width, collection))
  }
  
}