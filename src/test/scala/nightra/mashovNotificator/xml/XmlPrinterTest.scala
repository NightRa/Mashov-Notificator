//Created By Ilan Godik
package nightra.mashovNotificator.xml

import XmlPrinter._
import nightra.mashovNotificator.util.Printer._
import nightra.mashovNotificator.Test

class XmlPrinterTest extends Test {
  val att1 = Attribute("xmlns", "tempuri.org")
  val att2 = Attribute("name", "my name")
  val attributes = Vector(att1, att2)

  "Attribute show" should "have a space in the start" in {
    showAttribute(att1) should startWith(" ")
    showAttribute(att2) should startWith(" ")
  }

  it should "be of correct form" in {
    showAttribute(att1) should equal(" xmlns='tempuri.org'")
    showAttribute(att2) should equal(" name='my name'")
  }

  it should "be empty for an empty valued attribute" in {
    showAttribute(Attribute("xmlns", "")) should equal("")
  }

  "Attributes show" should "start with a space" in {
    showAttributes(attributes) should startWith(" ")
  }

  it should "be the concatenation of it's parts." in {
    showAttributes(attributes) should equal(showAttribute(att1) + showAttribute(att2))
  }

  "Show empty xml tag" should "show an empty tag properly" in {
    showEmptyXMLTag(Tag("div", "")) should equal("<div/>")
  }

  it should "show an empty tag with attributes properly" in {
    val xml = new XML {
      val label = "div"
      val attributes = XmlPrinterTest.this.attributes
      val text = ""
      val children = Seq.empty
    }

    showEmptyXMLTag(xml) should equal("<div xmlns='tempuri.org' name='my name'/>")
  }

  "Show non empty xml tag pretty" should "have all children and text indented with newlines and tabs" in {
    val xml =
      Tag("Login", "sample", "tempuri.org", Seq(
        Tag("id", "345741289"),
        Tag("misc", "more details", "", Seq(
          Tag("date", "03/05/2013")
        ))
      ))

    val expected =
      s"<Login xmlns='tempuri.org'>sample$line$tab<id>345741289</id>$line$tab<misc>more details$line$tab$tab<date>03/05/2013</date>$line$tab</misc>$line</Login>"

    val result = showNonEmptyXMLTagPretty(xml)

    result should be (expected)
  }

  it should "show xml with only text on one line" in {
    val xml = Tag("Login","details")

    val expected = "<Login>details</Login>"
    val result = showNonEmptyXMLTagPretty(xml)

    result should be (expected)
  }


}
