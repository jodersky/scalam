package scalam.m.ast

/**
 * Represents a matlab expression.
 * @define construct expression
 */
trait Expression extends Mable with Root {
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

/**
 * A matlab integer literal.
 * @define construct integer literal
 */
case class IntLiteral(x: Int) extends Expression

/**
 * A matlab double literal.
 * @define construct double literal
 */
case class DoubleLiteral(x: Double) extends Expression

/**
 * A matlab string literal.
 * @define construct string literal
 */
case class StringLiteral(x: String) extends Expression

/**
 * The matlab slice literal (:).
 * @define construct slice literal
 */
case object SliceLiteral extends Expression

/**
 * A matlab array or single-line matrix literal.
 * @param elements elements of the array
 *
 * @define construct array literal
 */
case class ArrayLiteral(elements: Expression*) extends Expression

/**
 * A matlab matrix literal.
 * @param rows rows of the matrix
 *
 * @define construct matrix literal
 */
case class MatrixLiteral(rows: Expression*) extends Expression

/**
 * A matlab variable access.
 * @param id identifier ("variable name") of the variable
 *
 * @define construct variable access
 */
case class Variable(id: Identifier) extends Expression

/**
 * A matlab matrix access by index.
 * @param id identifier of the variable to index
 * @param indices indices
 *
 * @define construct matrix access by index
 */
case class IndexMatrix(id: Identifier, indices: Expression*) extends Expression

/**
 * A matlab structure access by index.
 * @param id identifier of the variable to index
 * @param indices indices
 *
 * @define construct structure access by index
 */
case class IndexStructure(id: Identifier, indices: Expression*) extends Expression

/**
 * A matlab function call.
 * @param function identifier of the function
 * @param params parameters to pass to function call
 *
 * @define construct function call
 */
case class Function(function: Identifier, params: Expression*) extends Expression