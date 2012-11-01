package scalam.m.ast

/**
 * Trait common to all objects having a representation in the matlab programming language.
 * @define construct construct
 */
trait Mable {

  /** Generates the matlab code associated to this $construct. */
  def m: String
}

/**
 * Root (ie self-contained/top-level) syntactic elements of the matlab language inherit from this trait.
 * A root element in the matlab language is an element that may be contained on one line and optionally be terminated by a semi-colon.
 * Currently a root element is either a statement (assigment, etc...), a comment or an expression that is evaluated.
 */
trait Root {

  /** Returns the matlab code of this root element. */
  final def line: String = this match {
    case statement: Statement => statement.m + ";"
    case expression: Expression => expression.m + ";"
    case comment: Comment => comment.m
  }

  /**
   * Utility function to associate this root with and end-of-line comment.
   * Calling `line` on the root element returned by this method, will forward the call to `this.line` and append a comment.
   */
  def withComment(comment: Comment) = EOLComment(this, comment)
}

/**
 * Represents a matlab comment.
 * @define construct comment
 */
trait Comment extends Mable with Root {
  def m = this match {
    case SimpleComment(text) => "% " + text
    case DoubleComment(text) => "%% " + text
    case EOLComment(root, comment) => root.line + " " + comment.m
  }
}

/**
 * Represents a simple matlab comment. This type of comment begins with a single percent sign (%).
 * @define construct simple comment
 */
case class SimpleComment(text: String) extends Comment

/**
 * Represents a double matlab comment. This type of comment begins with a double percent sign (%%).
 * @define construct double comment
 */
case class DoubleComment(text: String) extends Comment

/**
 * Represents an end-of-line comment associated to another matlab root element (such as a statement).
 */
case class EOLComment(root: Root, comment: Comment) extends Comment


