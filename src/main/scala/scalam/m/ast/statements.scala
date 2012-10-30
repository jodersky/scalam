package scalam.m.ast

trait Statement extends Mable with Root{
  def m: String = this match {
    case Assign(id, value) => id.m + " = " + value.m
    case AssignMatrixIndex(id, indices, value) =>
      id.m + indices.map(_.m).mkString("(", ",", ")") + " = " + value.m
    
    case _ => throw new IllegalArgumentException("unkown statement: " + this)
  }
}

case class Assign(variable: Identifier, value: Expression) extends Statement
case class AssignMatrixIndex(variable: Identifier, indices: Seq[Expression], value: Expression) extends Statement
