package scalam
package m
package interpretation

import ast._
import scalax.file.Path


class OctaveInterpreter(pwd: Path) extends MInterpreter("octave", pwd)