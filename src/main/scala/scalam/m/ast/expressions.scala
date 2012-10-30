package scalam.m.ast

trait Expression extends Mable with Root{
  def m: String = this match {
    case IntLiteral(x) => x.toString
    case DoubleLiteral(x) => x.toString
    case StringLiteral(x) => "'" + x + "'"
    case SliceLiteral => ":"
    case ArrayLiteral(elements @ _*) => elements.map(_.m).mkString("[", ",", "]")
    case MatrixLiteral(rows @ _*) => rows.map(_.m).mkString("[", ";", "]")

    case Variable(id) => id.m
    case IndexMatrix(id, indices @ _*) => id.m + indices.map(_.m).mkString("(", ",", ")")
    case IndexStructure(id, indices @ _*) => id.m + indices.map(_.m).mkString("{", ",", "}")
    case Function(id, params @ _*) => id.m + params.map(_.m).mkString("(", ",", ")")

    case _ => throw new IllegalArgumentException("unkown expression: " + this)
  }
}

case class IntLiteral(x: Int) extends Expression
case class DoubleLiteral(x: Double) extends Expression
case class StringLiteral(x: String) extends Expression
case object SliceLiteral extends Expression
case class ArrayLiteral(elements: Expression*) extends Expression
case class MatrixLiteral(rows: Expression*) extends Expression
case class Variable(id: Identifier) extends Expression
case class IndexMatrix(id: Identifier, indices: Expression*) extends Expression
case class IndexStructure(id: Identifier, indices: Expression*) extends Expression
case class Function(function: Identifier, params: Expression*) extends Expression