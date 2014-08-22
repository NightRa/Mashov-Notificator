package nightra.mashovNotificator.gui.actions

import nightra.mashovNotificator.data.Credentials
import nightra.mashovNotificator.gui.controller.mainScene.{Events, MainSceneController}
import nightra.mashovNotificator.gui.description.MainSceneDescription
import nightra.mashovNotificator.gui.view.View
import nightra.mashovNotificator.language.DefaultEventStrings
import nightra.mashovNotificator.model.Event
import nightra.mashovNotificator.network.Requests
import nightra.mashovNotificator.util.Date

import scalaz.concurrent.Task
import scalaz.effect.IO
import scalaz.{-\/, \/-}

object LoadEvents {
  def eventsTask(credentials: Credentials): Task[Seq[Event]] =
    Requests.requestGradesAndBehaviorWithLogin(credentials, log = true) map {
      case (grades, behavior) => combineEvents(grades, behavior)
    }


  def updateNodesTask(credentials: Credentials, view: View[MainSceneDescription.type]): Task[IO[Unit]] =
    eventsTask(credentials).map(events => MainSceneController.updateSceneState(view, Events(events, DefaultEventStrings)))

  def runFetchEventsAndUpdateView(credentials: Credentials, view: View[MainSceneDescription.type]): IO[Unit] =
    IO {
      Task.fork(updateNodesTask(credentials, view)).runAsync {
        case -\/(exception) => println(exception)
        case \/-(io) => io.unsafePerformIO()
      }
    }



  def combineEvents(events: Seq[Event]*): Seq[Event] = events.flatten.sortBy(_.date)(Date.dateOrder.reverseOrder.toScalaOrdering)
}
