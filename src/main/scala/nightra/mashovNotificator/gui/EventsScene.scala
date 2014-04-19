//Created By Ilan Godik
package nightra.mashovNotificator.gui

import scalafx.geometry.{Insets, Pos, NodeOrientation}
import scalafx.scene.control.{ScrollPane, Tab, TabPane}
import scalafx.scene.layout._
import scalafx.scene.control.TabPane.TabClosingPolicy
import scalafx.scene.image.{Image, ImageView}
import scalafx.beans.property.ObjectProperty
import scalafx.scene.{Scene, Node}
import scalafx.scene.control.ScrollPane.ScrollBarPolicy

class EventsScene(events: ObjectProperty[Seq[Node]]) extends Scene(800, 600) {
  self =>

  val tabHeadings = Seq("ראשי", "מערכת שעות", "ציונים", "התנהגות", "הודעות")

  content = new StackPane {
    stylesheets = Seq("style.css")
    nodeOrientation = NodeOrientation.RIGHT_TO_LEFT

    content = Seq(tabPane, refreshImage)

    prefHeight <== self.height
    prefWidth <== self.width
  }

  def tabPane = {
    new TabPane {
      def mainTab = new Tab {
        text = tabHeadings.head
        content = new ScrollPane() {
          scrollPaneSelf =>
          content = new FlowPane {
            styleClass = Seq("mainPane")
            content = events.get
            events.onChange(content = events.get)

            prefHeight <== scrollPaneSelf.height
            prefWidth <== scrollPaneSelf.width
          }
          hbarPolicy = ScrollBarPolicy.NEVER
          vbarPolicy = ScrollBarPolicy.AS_NEEDED
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
    alignmentInParent = Pos.TOP_RIGHT
    margin = Insets(5)
  }
}
