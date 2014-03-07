//Created By Ilan Godik
package nightra.mashovNotificator.gui

import scalafx.geometry.{Insets, Pos, NodeOrientation}
import scalafx.scene.control.{Tab, TabPane}
import scalafx.scene.layout._
import scalafx.scene.control.TabPane.TabClosingPolicy
import scalafx.scene.image.{Image, ImageView}
import scalafx.beans.property.ObjectProperty
import scalafx.scene.Node

class GUI(events: ObjectProperty[Seq[Node]]) extends StackPane {
  self =>

  val tabHeadings = Seq("ראשי", "מערכת שעות", "ציונים", "התנהגות", "הודעות")

  stylesheets = Seq("style.css")
  nodeOrientation = NodeOrientation.RIGHT_TO_LEFT

  def tabPane = {
    new TabPane {
      def mainTab = new Tab {
        text = tabHeadings.head
        content = new FlowPane {
          styleClass = Seq("contentTab")
          content = events.get
          events.onChange(content = events.get)
        }
      }

      tabClosingPolicy = TabClosingPolicy.UNAVAILABLE

      tabs = mainTab +: tabHeadings.tail.map(header => new Tab {
        text = header
      })
    }
  }

  def refreshImage = new ImageView(new Image("refresh.png")) {
    styleClass = Seq("refresh-button")
    alignment = Pos.TOP_RIGHT
    margin = Insets(5)
  }

  content = Seq(tabPane, refreshImage)

}