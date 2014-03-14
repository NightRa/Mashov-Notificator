//Created By Ilan Godik
package nightra.mashovNotificator.network.requests

import nightra.mashovNotificator.xml.Tag
import nightra.mashovNotificator.network.{ReaderCompanion, ResponseCompanion, Response, Request}
import nightra.mashovNotificator.network.logic.{KeyBundle, Key}
import argonaut._, Argonaut._


case class BehaveEvent(studentID: Int, lessonID: Int, eventCode: Int, justified: Int, reporterID: Int, groupID: Int, lesson: Int,
                       lessonDate: String, name: String, justification: String, teacherName: String, subjectName: String, remarkText: String)

case class BehaveEventsResponse(events: Vector[BehaveEvent]) extends Response

object BehaveEvent extends ReaderCompanion[BehaveEvent] {
  implicit def reader =
    casecodec13(BehaveEvent.apply, BehaveEvent.unapply)("studentId", "lessonId", "eventCode", "justified",
      "reporterid", "groupid", "lesson", "lessondate", "name", "justification", "teacherName", "subjectName", "remarktext")
}

object BehaveEventsResponse extends ResponseCompanion[BehaveEventsResponse] {
  implicit def reader = casecodec1(BehaveEventsResponse.apply, BehaveEventsResponse.unapply)("tblStudentAchva")
}

case class BehaveEventsRequest(id: Int, key: Key) extends Request[BehaveEventsResponse] {
  def toXML =
    Tag("getStudentAchva", children = Seq(
      Tag("id", id.toString),
      Tag("CurrentYearS", key.key),
      Tag("startDate", "01/01/2001"),
      Tag("endDate", "01/01/2030")
    ))
}

object BehaveEventsRequest {
  def apply(keyBundle: KeyBundle): BehaveEventsRequest = BehaveEventsRequest(keyBundle.credentials.id, keyBundle.key)
}
