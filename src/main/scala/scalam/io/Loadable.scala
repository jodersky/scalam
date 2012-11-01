package scalam.io

import scalax.file.Path

trait Loadable[A] {
  def load(in: Path): A
}