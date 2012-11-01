package scalam

import scala.collection.generic.CanBuildFrom
import scala.language.higherKinds

/** Contains useful math functions. */
package object math {

  /**
   * Smooths a collection of numeric values using the moving-average algorithm.
   * The algorithm takes a window of width `width` that "slides" along the whole collection.
   * A new collection is created by taking the average of all points located in each window.
   *
   * @usecase def smooth[Elem, Coll[Elem]](collection: Coll[Elem], width: Int, passes: Int): Coll[Elem] = sys.error("")
   * 
   * @param collection the collection to smooth
   * @param width the window's witdh
   * @param passes how many times the collection is smoothed (i.e. how often smooth calls itself recursively)
   * @tparam Elem the type of the elements contained in the collection
   * @tparam Coll the type of the collection to be smoothed
   */
  def smooth[Elem, Coll[Elem]](collection: Coll[Elem], width: Int, passes: Int = 1)(implicit fractional: Fractional[Elem], cbf: CanBuildFrom[Coll[Elem], Elem, Coll[Elem]], view: Coll[Elem] => Seq[Elem]): Coll[Elem] = {
    def average(xs: Seq[Elem]): Elem = {
      import fractional._
      xs.sum / fromInt(xs.length)
    }

    if (passes <= 0) collection
    else {
      val b = cbf(collection)
      collection.sliding(width).foreach(neighbours => b += average(neighbours))
      smooth(b.result(), width, passes - 1)
    }
  }

}

package math{
  object Dummy
}
