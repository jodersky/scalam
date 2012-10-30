package scalam
package m
package interpretation

import ast._
import scalax.file.Path

class MatlabInterpreter(pwd: Path) extends MInterpreter("matlab -nosplash -nodesktop", pwd)