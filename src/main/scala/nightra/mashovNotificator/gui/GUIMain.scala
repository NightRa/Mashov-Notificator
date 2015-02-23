//Created By Ilan Godik
package nightra.mashovNotificator.gui

import nightra.mashovNotificator.data.Credentials
import nightra.mashovNotificator.gui.actions.LoadEvents
import nightra.mashovNotificator.gui.controller.mainScene.{Loading, MainSceneController}
import nightra.mashovNotificator.gui.view.mainScene.MainScene
import nightra.mashovNotificator.network.Runner
import nightra.mashovNotificator.util.IOTaskFuture

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage



object GUIMain extends JFXApp {
  // TODO: Input credentials
  val id = ???
  val password = ???
  val school = ???
  val year = ???

  val credentials = Credentials(id, password, school, year)

  // -------------------------------------------------------------------------------------------------------------------

  val initialState = Loading

  val mainScene = new MainScene(MainSceneController.stateToViewAction(initialState))

  stage = new PrimaryStage {
    scene = mainScene
  }


  IOTaskFuture.taskToIOHandleAndIgnore(LoadEvents.runFetchEventsAndUpdateView(credentials, mainScene)).unsafePerformIO()
  override def stopApp() = Runner.system.shutdown()

  // -------------------------------------------------------------------------------------------------------------------


}


