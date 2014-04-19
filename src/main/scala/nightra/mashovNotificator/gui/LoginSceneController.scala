package nightra.mashovNotificator.gui

import scalafx.Includes._
import nightra.mashovNotificator.language.{LoginStrings, DefaultLoginStrings}
import scalafx.scene.text.Text
import scalafx.animation.{Timeline, Transition}
import scala.language.postfixOps
import scalafx.beans.property.StringProperty
import scalaz.effect.IO
import scalaz.effect.IO._
import scalaz.syntax.semigroup._
import scalaz.std.anyVal.unitInstance
import scalaz.std.function._
import scalaz.syntax.functor._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage

class LoginSceneController(strings: LoginStrings) {

  import SideEffectImplicit._

  val status = new Text(strings.loggingIn)
  val loginScene = new LoginScene(strings, onLogin.map(_.unsafePerformIO()), status)

  def onLogin: (String, String) => IO[Unit] = (id, pass) =>
    putStrLn(s"ID: $id, Password: $pass") |+| showLoggingInStatus

  def showLoggingInStatus: IO[Unit] = IO(loggingInAnimation.play()) |+| loginScene.setGreenStatus

  def processStringAnimation(prop: StringProperty, message: String): Timeline =
    Timeline(Seq(
      at(0 s)(prop -> message),
      at(0.5 s)(prop -> (message + ".")),
      at(1 s)(prop -> (message + "..")),
      at(1.5 s)(prop -> (message + "...")),
      at(2 s)(prop -> (message + "...."))
    )).side(_.cycleCount = Transition.INDEFINITE)

  def loggingInAnimation: Timeline =
    processStringAnimation(status.text, strings.loggingIn)
}

object LoginScreenMain extends JFXApp {
  val loginController = new LoginSceneController(DefaultLoginStrings)
  stage = new PrimaryStage {
    scene = loginController.loginScene
  }
}
