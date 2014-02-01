//Created By Ilan Godik
package nightra.mashovNotificator.gui

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.geometry.NodeOrientation

object GUIMain extends JFXApp {
  stage = new PrimaryStage {
    scene = new Scene(800, 600) {
      sceneSelf =>
      content = new GUI {
        prefHeight <== sceneSelf.height
        prefWidth <== sceneSelf.width
      }
//      nodeOrientation = NodeOrientation.RIGHT_TO_LEFT
    }
  }

  println()
}
