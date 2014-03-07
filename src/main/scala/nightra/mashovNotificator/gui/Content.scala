//Created By Ilan Godik
package nightra.mashovNotificator.gui

import scalafx.scene.Node
import scalafx.scene.layout.{VBox, HBox}
import scalafx.scene.text.Text
import javafx.scene.text.TextFlow

object Content {
  type Field = Node

  def event(header: HBox, color: String, contentWidth: Int) = {
    data: List[Field] =>
      new VBox {
        styleClass = Seq("event", s"event-$color")
        prefWidth = contentWidth
        prefHeight = 150
        content = Seq(header, contentBox(data))
      }
  }

  def header(color: String, text: String) = new HBox {
    styleClass = Seq("header", s"header-$color")
    content = new Text(text) {
      styleClass = Seq("header-text")
    }
  }

  def contentBox(fields: List[Node]) = new VBox {
    styleClass = Seq("fields")
    content = fields
  }

  def field(header: String, content: String): Node = {
    val headerText = new Text(header + ":") {
      underline = true
    }
    val contentText = new Text(" " + content)
    new Node(new TextFlow(headerText, contentText)) {}
  }

  def gradeEvent = event(gradeHeader, "green", 175)
  def behaviorEvent = event(behaviorHeader, "red", 175)
  def messageEvent = event(messageHeader, "blue", 250)

  def gradeHeader = header("green", "ציון")
  def behaviorHeader = header("red", "הערת התנהגות")
  def messageHeader = header("blue", "הודעה")
}
