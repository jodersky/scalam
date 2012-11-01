package scalam.io

import scalax.io.Input

trait Loadable[A] {
  def load(in: Input): A
}