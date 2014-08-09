package nightra.mashovNotificator.gui.view.mainScene

import scalafx.beans.property.ObjectProperty
import scalafx.geometry.{Insets, Pos, NodeOrientation}
import scalafx.scene.Node
import scalafx.scene.control.ScrollPane.ScrollBarPolicy
import scalafx.scene.control.TabPane.TabClosingPolicy
import scalafx.scene.control.{ScrollPane, Tab, TabPane}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{FlowPane, StackPane}

class MainSceneContent(events: ObjectProperty[Seq[Node]]) extends StackPane {
  self =>

  val tabHeadings = Seq("ראשי", "מערכת שעות", "ציונים", "התנהגות", "הודעות")

  stylesheets = Seq("style.css")
  nodeOrientation = NodeOrientation.RIGHT_TO_LEFT

  def tabPane = {
    new TabPane {
      def mainTab = new Tab {
        text = tabHeadings.head
        content = new ScrollPane() {
          scrollPaneSelf =>
          content = new FlowPane {
            styleClass = Seq("contentTab")
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
    alignment = Pos.TOP_RIGHT
    margin = Insets(5)
  }

  content = Seq(tabPane, refreshImage)

}
