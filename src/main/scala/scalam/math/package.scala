package scalam

import scala.collection.SeqLike
import scala.collection.generic.CanBuildFrom
package object math {

  def smooth[Elem, Coll[Elem]](width: Int, passes: Int, collection: Coll[Elem])(implicit fractional: Fractional[Elem], cbf: CanBuildFrom[Coll[Elem], Elem, Coll[Elem]], view: Coll[Elem] => Seq[Elem]): Coll[Elem] = {
    def average(xs: Seq[Elem]): Elem = {
      import fractional._
      xs.sum / fromInt(xs.length)
    }

    if (passes <= 0) collection
    else {
      val b = cbf(collection)
      collection.sliding(width).foreach(neighbours => b += average(neighbours))
      smooth(width, passes - 1, b.result())
    }
  }
  
}