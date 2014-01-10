//Created By Ilan Godik
package nightra.mashovNotificator.xml

trait XML {
  import XmlPrinter._

  val label: String
  val text: String
  val attributes: Seq[Attribute]
  val children: Seq[XML]

  def isEmpty = text.isEmpty && children.isEmpty

  override def toString = showXMLPretty.shows(this)
}

case class Attribute(key: String, value: String)

/**
 * Attention: Simplified xml model without attributes.
 * This is what's used for requests.
 **/
case class Tag(label: String, text: String, namespace: String = "", children: Seq[Tag] = Seq.empty) extends XML{
  val attributes = Seq(Attribute("xmlns", namespace))
}

