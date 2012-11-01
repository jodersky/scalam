package scalam.m.ast

/**
 * A matlab identifier.
 * @name name of variable (this must be a valid matlab identifier string)
 *
 * @define construct identifier
 */
case class Identifier(name: String) extends Mable {

  def m = name

  def toValid = {
    val word = name.filter(c => c.isLetterOrDigit || c == '_')
    val id = word.headOption match {
      case None => "x"
      case Some(c) => if (!c.isLetter) 'x' + word else word
    }
    Identifier(id)
  }
}