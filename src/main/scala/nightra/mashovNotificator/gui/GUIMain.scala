//Created By Ilan Godik
package nightra.mashovNotificator.gui

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage

import nightra.{mashovNotificator => m}
import nightra.mashovNotificator.model.{BehaviorEvent, Grade, Event}
import m.util.Date
import nightra.mashovNotificator.language.DefaultEventStrings
import nightra.mashovNotificator.main.DefaultRunner
import nightra.mashovNotificator.network.Task
import nightra.mashovNotificator.network.unsafe.RequestRunner
import nightra.mashovNotificator.data.Credentials
import nightra.mashovNotificator.tasks.Requests
import nightra.mashovNotificator.gui.ModelGUIs._

object GUIMain extends JFXApp {
  self =>
  val eventStrings = DefaultEventStrings

  val mainRunner = new DefaultRunner
  val requestRunner = new RequestRunner {
    val actorSystem = mainRunner.system
  }

  val requests = new Requests {
    val requestRunner = self.requestRunner
  }

  // TODO: Node indicating loading.
  val defaultEvents = Seq(Grade("ספרות", "מבחן מחצית", 97, Date(29, 1, 2014)), BehaviorEvent("אנגלית", "העדרות", "אוניברסיטה", Date(29, 1, 2014)))

  val eventScene = new EventScene(eventsGUI(eventStrings)(defaultEvents))

  stage = new PrimaryStage {
    scene = eventScene
  }

  val id = ???
  val password = ???
  val school = ???
  val year = ???

  val credentials = Credentials(id, password, school, year)

  val combinedEvents = for {
    (grades, behaviorEvents) <- requests.requestGradesAndBehaviorWithLogin(credentials).logFailure(_ => "Combined Request failed.")
  } yield combineEvents(grades, behaviorEvents)

  def nodes(events: Task[Seq[Event]]) = events.map(eventsGUI(eventStrings))

  val updateNodes = nodes(combinedEvents).onSuccess(eventScene.setNodes)

  updateNodes.run(mainRunner.executionContent).unsafePerformIO()

  def combineEvents(events: Seq[Event]*): Seq[Event] = events.flatten.sortBy(_.date)(Date.dateOrder.reverseOrder.toScalaOrdering)

  override def stopApp() = mainRunner.system.shutdown()
  println()
}


