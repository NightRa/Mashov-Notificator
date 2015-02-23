/*
package nightra.mashovNotificator.root

import java.awt.MenuItem
import java.awt.PopupMenu
import java.awt.SystemTray
import java.awt.TrayIcon
import java.awt.event.ActionListener
import java.net.URL
import javafx.collections.ObservableList
import scalafx.application.Platform
import scalafx.scene.control.Button
import scalafx.scene.input.{KeyCode, KeyEvent}
import scalafx.stage.Stage
import javax.imageio.ImageIO
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.{Node, Scene}
import scalafx.Includes._

/**
 *
 * @author alvaro
 */
object TrayTest  extends JFXApp  {
  val nodes: ObservableList[Node] = javafx.collections.FXCollections.observableArrayList(new Button("Hello!"))

  stage = new PrimaryStage {
    width = 800
    height = 600
    scene = new Scene {
      content = nodes
      onKeyPressed = (event: KeyEvent) => if (event.code == KeyCode.F5) println("hello")
    }

  }
  createTrayIcon(stage)
  Platform.implicitExit = false


  def createTrayIcon(stage: Stage) {
    if (SystemTray.isSupported) {
      val tray: SystemTray = SystemTray.getSystemTray
      val url: URL = new URL("http://www.digitalphotoartistry.com/rose1.jpg")
      val image = ImageIO.read(url)

      val closeListener: ActionListener = new ActionListener {
        def actionPerformed(e: java.awt.event.ActionEvent) {
          Platform.exit()
        }
      }
      val showListener: ActionListener = new ActionListener {
        def actionPerformed(e: java.awt.event.ActionEvent) {
          Platform.runLater(stage.show())
        }
      }
      val popup: PopupMenu = new PopupMenu
      val showItem = new MenuItem("Show")
      showItem.addActionListener(showListener)
      popup.add(showItem)

      val closeItem = new MenuItem("Close")
      closeItem.addActionListener(closeListener)
      popup.add(closeItem)

      val trayIcon = new TrayIcon(image, "Title", popup)
      trayIcon.addActionListener(showListener)
      tray.add(trayIcon)

      stage.setOnCloseRequest(() => hide(stage, trayIcon))
    }
  }

  def showProgramIsMinimizedMsg(trayIcon: TrayIcon) {
    trayIcon.displayMessage("Some message.", "Some other message.", TrayIcon.MessageType.INFO)
  }

  def hide(stage: Stage, trayIcon: TrayIcon) {
    if (SystemTray.isSupported) {
      stage.hide()
      showProgramIsMinimizedMsg(trayIcon)
    }
    else {
      System.exit(0)
    }
  }

}

*/
