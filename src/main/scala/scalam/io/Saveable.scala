package scalam.io

import scalax.file.Path

trait Saveable {
  
  def save(out: Path): Unit
  
}