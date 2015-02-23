package nightra.mashovNotificator.gui.actions

import java.io.File

import nightra.mashovNotificator.data.Credentials
import nightra.mashovNotificator.fileIO.EventCache
import nightra.mashovNotificator.gui.controller.mainScene.{Events, MainSceneController}
import nightra.mashovNotificator.gui.description.MainSceneDescription
import nightra.mashovNotificator.gui.view.View
import nightra.mashovNotificator.language.DefaultEventStrings
import nightra.mashovNotificator.model.{BehaviorEvent, Event, Grade}
import nightra.mashovNotificator.network.Requests
import nightra.mashovNotificator.util.Date

import scalaz.concurrent.Task
import scalaz.effect.IO

object LoadEvents {
  import nightra.mashovNotificator.util.IOTaskFuture._

  def updateView(view: View[MainSceneDescription.type]): ((Seq[Grade], Seq[BehaviorEvent])) => IO[Unit] = events =>
    MainSceneController.updateSceneState(view, Events(combineFetchedEvents(events), DefaultEventStrings))

  def runFetchEventsAndUpdateView(credentials: Credentials, view: View[MainSceneDescription.type]): Task[Unit] =
    for{
      events <- Requests.requestGradesAndBehaviorWithLogin(credentials, log = true)
      _ <- cacheEvents(events)
      _ <- updateView(view)(events)
    } yield ()
  // Potential Non-determinism:
  /*taskToIOHandleAndIgnore(
    Requests.requestGradesAndBehaviorWithLogin(credentials, log = true).flatMap { events =>
      Nondeterminism[Task].both(
        cacheEvents(events),
        updateView(view)(events)
      )
    }
  )*/


  def cacheEvents: ((Seq[Grade], Seq[BehaviorEvent])) => Task[Unit] = {
    case (grades, behavior) =>
      for{
        _ <- EventCache.saveGrades(grades, new File("grades.save"))
        _ <- EventCache.saveBehaviorEvents(behavior, new File("behavior.save"))
      } yield ()
      // Potential Non-determinism:
      /*Nondeterminism[Task].mapBoth(
        EventCache.saveGrades(grades, new File("grades.save")),
        EventCache.saveBehaviorEvents(behavior, new File("behavior.save"))
      )((_, _) => ())*/
  }

  def combineFetchedEvents: ((Seq[Grade], Seq[BehaviorEvent])) => Seq[Event] = {
    case (grades, behavior) => combineEvents(grades, behavior)
  }

  def combineEvents(events: Seq[Event]*): Seq[Event] = events.flatten.sortBy(_.date)(Date.dateOrder.reverseOrder.toScalaOrdering)
}
