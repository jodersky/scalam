package scalam.m.ast

/**
 * A matlab identifier.
 * @param name name of variable (this must be a valid matlab identifier string)
 *
 * @define construct identifier
 */
case class Identifier(name: String) extends Mable {
  def m = name
}

object Identifier {

  def makeValid(raw: String) = {
    val transformSymbols = Map(' ' -> '_').withDefault(c => c)

    val validChars = raw.map(c => transformSymbols(c)).filter(c => c.isLetterOrDigit || c == '_')

    validChars.headOption match {
      case Some(c) if (!c.isLetter) => 'x' + validChars
      case Some(c) => validChars
      case None => "id"
    }
  }

}