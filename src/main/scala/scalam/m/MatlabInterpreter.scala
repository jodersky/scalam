package scalam.m

import ast._
import scalax.file.Path

class MatlabInterpreter(pwd: Path) extends Interpreter("matlab -nosplash -nodesktop", pwd) {
  def evaluate(statement: ast.Statement) = write(statement.m + "\n")
  def exit() = {
    val cmd = Evaluate(Function(Identifier("exit")))
    evaluate(cmd)
    super.close()
  }
  
}

object MatlabInterpreter {
  final val command = "matlab -nosplash -nodesktop"
}