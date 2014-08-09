//Created By Ilan Godik
package nightra.mashovNotificator.model

import nightra.{mashovNotificator => m}
import m.util.Date
import BehaviorEvent.{Justification, BehaviorType}
import argonaut.Argonaut.casecodec4


/** data Event = GradeEvent: subject,topic,grade,date | BehaviorEvent: subject,type,justification,date */

sealed trait Event {
  val date: Date
}

case class Grade(subject: String, topic: String, grade: Int, date: Date) extends Event

case class BehaviorEvent(subject: String, `type`: BehaviorType, justification: Justification, date: Date) extends Event



/**
 * Json Codecs
 **/

object Grade {
  implicit val codec = casecodec4(Grade.apply, Grade.unapply)("subject", "topic", "grade", "date")
}

object BehaviorEvent {
  type BehaviorType = String
  type Justification = String

  implicit val codec = casecodec4(BehaviorEvent.apply, BehaviorEvent.unapply)("subject", "type", "justification", "date")
}


