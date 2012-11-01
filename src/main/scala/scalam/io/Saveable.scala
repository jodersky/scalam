package scalam.io

import scalax.io.Output

trait Saveable {
  def save(out: Output)
}