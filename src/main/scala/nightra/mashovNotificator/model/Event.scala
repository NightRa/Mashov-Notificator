//Created By Ilan Godik
package nightra.mashovNotificator.model

import nightra.{mashovNotificator => m}
import m.util.Date
import BehaviorEvent.{Justification, BehaviorType}

sealed trait Event {
  val date: Date
}

case class Grade(subject: String, topic: String, grade: Int, date: Date) extends Event

case class BehaviorEvent(subject: String, date: Date, `type`: BehaviorType, justification: Justification) extends Event

object BehaviorEvent {
  type BehaviorType = String
  type Justification = String
}


