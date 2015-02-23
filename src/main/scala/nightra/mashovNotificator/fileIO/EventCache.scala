//Created By Ilan Godik
package nightra.mashovNotificator.fileIO

import java.io.File

import argonaut.Argonaut._
import nightra.mashovNotificator.fileIO.FileIO._
import nightra.mashovNotificator.model.{BehaviorEvent, Grade}

import scalaz.\/
import scalaz.effect.IO

object EventCache {
  def gradesJsonString(grades: Seq[Grade]): String = grades.toVector.asJson.spaces2
  def saveGrades(grades: Seq[Grade], file: File): IO[Unit] = writeFile(gradesJsonString(grades), file)
  def getSavedGrades(file: File): IO[String \/ List[Grade]] =
    readJson[List[Grade]](file)

  def behaviorEventsJsonString(behaviorEvents: Seq[BehaviorEvent]): String = behaviorEvents.toVector.asJson.spaces2
  def saveBehaviorEvents(behaviorEvents: Seq[BehaviorEvent], file: File): IO[Unit] = writeFile(behaviorEventsJsonString(behaviorEvents), file)
  def getSavedBehaviorEvents(file: File): IO[String \/ List[BehaviorEvent]] =
    readJson[List[BehaviorEvent]](file)
}
