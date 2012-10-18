package scalam.m

import scalax.file.Path

class MatlabInterpreter(pwd: Path = Path(".")) extends Interpreter("matlab -nosplash -nodesktop", pwd) {
  
  def evaluate(statement: ast.Statement) = write(statement.m)
  
}