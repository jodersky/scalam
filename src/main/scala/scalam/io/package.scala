package scalam

import scalax.file.Path
import breeze.linalg._

package object io {

  def load[A: Loadable](path: Path): A = {
    val loadable = implicitly[Loadable[A]]
    loadable.load(path)
  }

  def save[A <% Saveable](objectToSave: A, path: Path) = {
    objectToSave.save(path)
  }
  
}