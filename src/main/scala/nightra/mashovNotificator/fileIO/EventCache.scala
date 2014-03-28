//Created By Ilan Godik
package nightra.mashovNotificator.fileIO

import nightra.mashovNotificator.model.{BehaviorEvent, Grade}
import FileIO._
import scalaz.effect.IO
import argonaut._, Argonaut._
import java.io.File
import scalaz.\/

object EventCache {
  def gradesJsonString(grades: List[Grade]): String = grades.asJson.spaces2
  def saveGrades(grades: List[Grade], file: File): IO[Unit] = writeFile(gradesJsonString(grades), file)
  def getSavedGrades(file: File): IO[String \/ List[Grade]] =
    readJson[List[Grade]](file)

  def behaviorEventsJsonString(behaviorEvents: List[BehaviorEvent]): String = behaviorEvents.asJson.spaces2
  def saveBehaviorEvents(behaviorEvents: List[BehaviorEvent], file: File): IO[Unit] = writeFile(behaviorEventsJsonString(behaviorEvents), file)
  def getSavedBehaviorEvents(file: File): IO[String \/ List[BehaviorEvent]] =
    readJson[List[BehaviorEvent]](file)
}
