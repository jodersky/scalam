package scalam.m.ast

trait Mable { def m: String }

//top level m constructs
case class Identifier(name: String) extends Mable {
  def m: String = name

  def toValid = {
    val word = name.filter(c => c.isLetterOrDigit || c == '_')
    val id = word.headOption match {
      case None => sys.error("")
      case Some(c) => if (!c.isLetter) 'x' + word else word
    }
    Identifier(id)
  }
}
trait Expression extends Mable
trait Statement extends Mable
case class Comment(text: String) extends Mable {def m = "% " + text}

//expressions
case class IntLiteral(x: Int) extends Expression { def m = x.toString }
case class DoubleLiteral(x: Double) extends Expression { def m = x.toString }
case class StringLiteral(x: String) extends Expression { def m = "'" + x.toString + "'" }
case object SliceLiteral extends Expression { def m = ":" }
case class ArrayLiteral(elements: Expression*) extends Expression {
  def m = elements.map(_.m).mkString("[", ",", "]")
}
case class MatrixLiteral(rows: Expression*) extends Expression {
  def m = rows.map(_.m).mkString("[", ";", "]")
}
case class Variable(id: Identifier) extends Expression { def m = id.m }
case class IndexMatrix(id: Identifier, indices: Expression*) extends Expression {
  def m = id.m + indices.map(_.m).mkString("(", ",", ")")
}
case class IndexStructure(id: Identifier, indices: Expression*) extends Expression {
  def m = id.m + indices.map(_.m).mkString("{", ",", "}")
}
case class Function(function: Identifier, params: Expression*) extends Expression {
  def m = function.m + params.map(_.m).mkString("(", ",", ")")
}

//statements
case class Assign(variable: Identifier, value: Expression) extends Statement {
  def m = variable.m + " = " + value.m + ";"
}
case class AssignMatrixIndex(variable: Identifier, indices: Seq[Expression], value: Expression) extends Statement {
  def m = variable.m + indices.map(_.m).mkString("(", ",", ")") + " = " + value.m + ";"
}

case class Evaluate(expression: Expression) extends Statement {
  def m = expression.m + ";"
}