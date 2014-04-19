package nightra.mashovNotificator.gui

import scalafx.Includes._
import scalafx.scene.Scene
import scalafx.geometry.{Pos, Insets, NodeOrientation}
import scalafx.scene.layout._
import scalafx.scene.control._
import nightra.mashovNotificator.language.LoginStrings
import scalafx.scene.text.Text
import scalaz.effect.IO

class LoginScene(strings: LoginStrings, onLoginButton: (String, String) => Unit, notificationText: Text) extends Scene(800, 600) {
  self =>

  import SideEffectImplicit._

  stylesheets = Seq("style.css")
  nodeOrientation = NodeOrientation.RIGHT_TO_LEFT

  val status = new HBox() {
    styleClass = Seq("footer")

    content = notificationText.side(_.styleClass = Seq("header-text"))
  }

  def setGreenStatus: IO[Unit] = IO {
    status.styleClass = Seq("footer", "header-green")
  }

  def setRedStatus: IO[Unit] = IO {
    status.styleClass = Seq("footer", "header-red")
  }

  content = new StackPane {
    styleClass = Seq("mainPane")
    content = new BorderPane() {
      vbox =>
      styleClass = Seq("event", "event-green")
      top = Content.header("green", strings.loginHeader).side(_.prefWidth <== vbox.width)

      center = new GridPane {
        styleClass = Seq("loginGrid")

        val idField = new TextField().side(_.promptText = strings.id)
        val passField = new PasswordField().side(_.promptText = strings.pass)
        addRow(1, Label(strings.id).side(_.labelFor = idField), idField)
        addRow(2, Label(strings.pass).side(_.labelFor = passField), passField)

        val loginButton = new Button(strings.loginButton)
        loginButton.alignmentInParent = Pos.CENTER
        loginButton.defaultButton = true
        loginButton.onAction = () => onLoginButton(idField.text.value, passField.text.value)

        add(loginButton, 0, 3, 2, 1)

      }

      bottom = status


      prefWidth <== self.width * 0.6
      prefHeight <== self.height * 0.6
      maxWidth <== prefWidth
      maxHeight <== prefHeight
    }


    prefHeight <== self.height
    prefWidth <== self.width
  }

}
