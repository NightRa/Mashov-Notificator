package nightra.mashovNotificator.fileIO

import nightra.mashovNotificator.Test
import EventCache._
import nightra.mashovNotificator.model.{BehaviorEvent, Grade}
import nightra.mashovNotificator.util.Date
import scalaz.syntax.id._

class EventCacheTest extends Test {

  "Save grades" should "have an identity." in {
    val grades = List(Grade("English", "Mid-Exam", 100, Date(12, 3, 2014)), Grade("Hebrew", "Final-Exam", 100, Date(15, 6, 2014)))
    val file = testFile("grades.save")
    val saveLoad = for {
      _ <- saveGrades(grades, file)
      loadedGrades <- getSavedGrades(file)
    } yield loadedGrades

    saveLoad.unsafePerformIO() should equal(grades.right)
  }

  "Save behavior events" should "have an identity." in {
    val behaviorEvents = List(BehaviorEvent("English", "miss", "Didn't wake up", Date(22, 1, 2014)), BehaviorEvent("Citizenship", "miss", "None", Date(18, 2, 2013)))
    val file = testFile("behavior.save")
    val saveLoad = for {
      _ <- saveBehaviorEvents(behaviorEvents, file)
      loadedBehaviorEvents <- getSavedBehaviorEvents(file)
    } yield loadedBehaviorEvents

    saveLoad.unsafePerformIO() should equal(behaviorEvents.right)
  }
}
