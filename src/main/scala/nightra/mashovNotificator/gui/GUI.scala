//Created By Ilan Godik
package nightra.mashovNotificator.gui

import scalafx.geometry.{Insets, Pos, NodeOrientation}
import scalafx.scene.control.{Tab, TabPane}
import scalafx.scene.layout._
import scalafx.scene.control.TabPane.TabClosingPolicy
import scalafx.scene.text.Text
import scalafx.scene.image.{Image, ImageView}

class GUI extends StackPane {
  self =>
  stylesheets = Seq("style.css")
  nodeOrientation = NodeOrientation.RIGHT_TO_LEFT

  def tabPane = {
    new TabPane{

      val tabHeadings = Seq("ראשי", "מערכת שעות", "ציונים", "התנהגות", "הודעות")

      val mainTab = new Tab {
        text = tabHeadings.head
        content = new FlowPane {
          styleClass = Seq("contentTab")
          content = Seq(gradeEvent,behaviorEvent,messageEvent,gradeEvent,messageEvent,behaviorEvent)
        }
      }

      def event(header:HBox,color:String,contentWidth:Int) = new VBox {
        alignment = Pos.TOP_RIGHT
        styleClass = Seq("event", s"event-$color")
        prefWidth = contentWidth
        prefHeight = 110
        content = Seq(header)
      }

      def header(color:String,text:String) = {
        new HBox {
          layoutY = -2
          alignment = Pos.TOP_CENTER
          styleClass = Seq("header", s"header-$color")
          content = new Text(text){
            styleClass = Seq("header-text")
          }
        }
      }

      def gradeEvent = event(gradeHeader,"green",125)
      def behaviorEvent = event(behaviorHeader,"red",125)
      def messageEvent = event(messageHeader,"blue",250)

      def gradeHeader = header("green","ציון")
      def behaviorHeader = header("red","הערת התנהגות")
      def messageHeader = header("blue","הודעה")

      tabClosingPolicy = TabClosingPolicy.UNAVAILABLE

      tabs = mainTab +: tabHeadings.tail.map(header => new Tab {
        text = header
      })
    }
  }
  def refreshImage = new ImageView(new Image("refresh.png")){
//    styleClass = Seq("refresh-button")
    alignment = Pos.TOP_RIGHT
    margin = Insets(5)
  }

  content = Seq(tabPane,refreshImage)

}
