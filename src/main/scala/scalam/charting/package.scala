package scalam

import scalam.charting.MatlabChart
import scalam.charting.HSV
import breeze.linalg.DenseVector

package object charting {
  
  //test
  def plot(dataSets: Seq[(DenseVector[Double], DenseVector[Double])]) = {
    val ds: Seq[DataSet] = dataSets.map{case (xs, ys) => DataSet(xs, ys)}
    val c = new MatlabChart(ds, "title", "x", "y", colorScheme = new HSV(ds))
    c.save()
    c.run()
  }

}