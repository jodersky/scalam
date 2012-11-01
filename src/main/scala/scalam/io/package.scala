package scalam

import scalax.file.Path
import breeze.linalg._

package object io {

  def load[A: Loadable](path: Path): A = {
    val loadable = implicitly[Loadable[A]]
    loadable.load(path)
  }

  def save[A <% Saveable](objectToSave: A, path: Path) = {
    path.createFile(createParents = true, failIfExists = false)
    for (processor <- path.outputProcessor; out = processor.asOutput)
      objectToSave.save(out)
  }
  
}