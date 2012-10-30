package scalam.m.ast

case class Identifier(name: String) extends Mable{
  
  def m = name
  
  def toValid = {
    val word = name.filter(c => c.isLetterOrDigit || c == '_')
    val id = word.headOption match {
      case None => sys.error("")
      case Some(c) => if (!c.isLetter) 'x' + word else word
    }
    Identifier(id)
  }
}