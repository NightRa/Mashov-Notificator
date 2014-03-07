//Created By Ilan Godik
package nightra.mashovNotificator.network.requests

import nightra.mashovNotificator.network.{Response, ResponseCompanion, ReaderCompanion, Request}
import nightra.mashovNotificator.network.logic.{KeyBundle, Key}
import nightra.mashovNotificator.xml.Tag
import nightra.mashovNotificator.network.readers.{GradesReader, GradeReader}

case class GradeResponse(subjectName: String, name: String, grade: Int, gradeComment: Option[String], rate: Double, teacherName: String, eDate: String, visibleFrom: String)

object GradeResponse extends ReaderCompanion[GradeResponse] {
  implicit val reader = GradeReader
}

case class GradesResponse(grades: Seq[GradeResponse]) extends Response

object GradesResponse extends ResponseCompanion[GradesResponse] {
  implicit val reader = GradesReader
}

case class GradesRequest(id: Int, key: Key) extends Request[GradesResponse] {
  def toXML = Tag("getStudentGrades", children = Seq(
    Tag("studentId", id.toString),
    Tag("CurrentYearS", key.key),
    Tag("startDate", "01/01/2001"),
    Tag("endDate", "01/01/2030")
  ))
}

object GradesRequest {
  def apply(keyBundle: KeyBundle): GradesRequest = GradesRequest(keyBundle.credentials.id, keyBundle.key)
}
