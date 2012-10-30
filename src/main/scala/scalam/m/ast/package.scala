package scalam.m.ast

/** Trait common to all objects having a representation in the matlab programming language. */
trait Mable {
  def m: String
}

trait Root {
  final def line: String = this match {
    case statement: Statement => statement.m + ";"
    case expression: Expression => expression.m + ";"
    case comment: Comment => comment.m
  }
}

trait Comment extends Mable with Root {
  def m = this match {
    case SimpleComment(text) => "% " + text
    case DoubleComment(text) => "%% " + text
    case EOLComment(root, comment) => root.line + " " + comment.m
  }
}

case class SimpleComment(text: String) extends Comment
case class DoubleComment(text: String) extends Comment
case class EOLComment(root: Root, comment: Comment) extends Comment


