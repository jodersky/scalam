package scalam.plotting


/** Helper class used for implicit font size specification. A font size is nothing but an Int,
 * but defining an implicit of type int could pollute a lot of code. */
case class FontSize(fs: Int)