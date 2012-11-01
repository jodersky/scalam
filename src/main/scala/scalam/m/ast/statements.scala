package scalam.m.ast

/**
 * A matlab statement.
 * @define construct statement
 */
trait Statement extends Mable with Root {
  def m: String = this match {
    case Assign(id, value) => id.m + " = " + value.m
    case AssignMatrixIndex(id, indices, value) =>
      id.m + indices.map(_.m).mkString("(", ",", ")") + " = " + value.m

    case _ => throw new IllegalArgumentException("unkown statement: " + this)
  }
}

/**
 * Variable assignment.
 * @param variable identifer of variable
 * @param value value to assign
 *
 * @define construct assignment
 */
case class Assign(variable: Identifier, value: Expression) extends Statement

/**
 * Variable (matrix) index assignment.
 * @param variable identifer of variable
 * @param indices indices of variable
 * @param value value to assign
 *
 * @define construct variable (matrix) assignment
 */
case class AssignMatrixIndex(variable: Identifier, indices: Seq[Expression], value: Expression) extends Statement
