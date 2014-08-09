//Created By Ilan Godik
package nightra.mashovNotificator.gui

import nightra.mashovNotificator.data.Credentials
import nightra.mashovNotificator.gui.controller.MainSceneController
import nightra.mashovNotificator.gui.view.mainScene.{Events, MainScene, MainSceneState, Loading}
import nightra.mashovNotificator.language.DefaultEventStrings
import nightra.mashovNotificator.model.{DomainMorphism, Event}
import nightra.mashovNotificator.network.{Runner, Requests}
import nightra.mashovNotificator.network.logic.KeyBundle
import nightra.mashovNotificator.network.requests.{BehaveEventsResponse, GradesResponse}
import nightra.mashovNotificator.util.Date

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalaz.Scalaz._
import scalaz.concurrent.Task
import scalaz.effect.IO
import scalaz.{Nondeterminism, -\/, \/-}



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

  def updateSceneState(state: MainSceneState): IO[Unit] =
    mainScene.applyViewAction(MainSceneController.stateToViewAction(state))

  override def stopApp() = Runner.system.shutdown()

  // -------------------------------------------------------------------------------------------------------------------


  val keyTask: Task[KeyBundle] = retryLog(Requests.requestKey(credentials))("Key")
  def parallelRequests(key: KeyBundle): Task[(GradesResponse, BehaveEventsResponse)] = Nondeterminism[Task].both(
    retryLog(Requests.requestGrades(key))("Grades"),
    retryLog(Requests.requestBehaviorEvents(key))("Behavior")
  )
  val eventResponses: Task[(GradesResponse, BehaveEventsResponse)] =
    repeat(for {
      key <- keyTask
      result <- parallelRequests(key)
    } yield result)("RETRYING...")

  val domainEventsTask = eventResponses map {
    case (gradesResp, behaviorResp) => (DomainMorphism(gradesResp), DomainMorphism(behaviorResp))
  }

  def retryLog[A](task: Task[A])(description: String) =
    log(repeat(task)(s"$description failure."), s"Starting $description Task.", s"Finished $description Task.")


  def log[A](task: Task[A], start: String, end: String): Task[A] = logTask(start) >> Task.delay(Thread.sleep(250)) >> task.onFinish(_ => logTask(end))

  def logTask(message: String) = Task.delay(println(message))

  def repeat[A](task: Task[A])(message: String, amount: Int = 5): Task[A] = {
    if (amount == 0) task
    else {
      handle(task)(message).handleWith {
        case _ => repeat(task)(message, amount - 1)
      }
    }
  }

  def handler(message: String): Option[Throwable] => Task[Unit] = {
    case Some(ex) => Task.delay(println(message))
    case None => Task.now(())
  }

  def handle[A](task: Task[A])(message: String): Task[A] = task.onFinish(handler(message))

  val eventsTask: Task[Seq[Event]] = domainEventsTask map {
    case (grades, behavior) => combineEvents(grades, behavior)
  }

  val updateNodesTask = eventsTask.map(events => updateSceneState(Events(events, DefaultEventStrings)))

  println()

  Task.fork(updateNodesTask).runAsync {
    case -\/(exception) => println(exception)
    case \/-(io) => io.unsafePerformIO()
  }

  def combineEvents(events: Seq[Event]*): Seq[Event] = events.flatten.sortBy(_.date)(Date.dateOrder.reverseOrder.toScalaOrdering)



}


