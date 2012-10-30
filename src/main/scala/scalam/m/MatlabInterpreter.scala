package scalam.m

import ast._
import scalax.file.Path

class MatlabInterpreter(pwd: Path) extends Interpreter("matlab -nosplash -nodesktop", pwd) {
  def evaluate(root: ast.Root) = write(root.line + "\n")
  def exit() = {
    val cmd = Function(Identifier("exit"))
    evaluate(cmd)
    super.close()
  }
  
}

object MatlabInterpreter {
  final val command = "matlab -nosplash -nodesktop"
}