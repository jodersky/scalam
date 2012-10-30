package scalam.plotting

/**
 * Helper class used for implicit font size specification. Although a font size is typically represented by an Int,
 * a custom class is used as to avoid code pollution when using implicits.
 */
case class FontSize(fontSize: Int)