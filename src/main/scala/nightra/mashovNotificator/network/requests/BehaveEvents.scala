//Created By Ilan Godik
package nightra.mashovNotificator.network.requests

import nightra.mashovNotificator.xml.Tag
import nightra.mashovNotificator.network.{ReaderCompanion, ResponseCompanion, Response, Request}
import nightra.mashovNotificator.network.logic.Key
import nightra.mashovNotificator.network.readers.{BehaveEventsReader, BehaveEventReader}

case class BehaveEventsRequest(id: Int, key: Key) extends Request[BehaveEventsResponse] {
  def toXML =
    Tag("getStudentAchva", children = Seq(
      Tag("id", id.toString),
      Tag("CurrentYearS", key.key),
      Tag("startDate", "01/01/2001"),
      Tag("endDate", "01/01/2030")
    ))
}

case class BehaveEvent(studentID: Int, lessonID: Int, eventCode: Int, justified: Int, reporterID: Int, groupID: Int, lesson: Int,
                       lessonDate: String, name: String, justification: String, teacherName: String, subjectName: String, remarkText: String)

object BehaveEvent extends ReaderCompanion[BehaveEvent] {
  implicit val reader = BehaveEventReader
}

case class BehaveEventsResponse(events: Seq[BehaveEvent]) extends Response

object BehaveEventsResponse extends ResponseCompanion[BehaveEventsResponse] {
  implicit val reader = BehaveEventsReader
}
