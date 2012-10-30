package scalam
package m
package interpretation

import ast._
import scalax.file.Path

class MInterpreter(command: String, pwd: Path) extends Interpreter(command, pwd){
  
  def evaluate(root: ast.Root) = write(root.line + "\n")
  
  def exit() = {
    val cmd = Function(Identifier("exit"))
    evaluate(cmd)
    super.close()
  }

}