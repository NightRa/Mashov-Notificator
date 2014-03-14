//Created By Ilan Godik
package nightra.mashovNotificator.network.requests

import nightra.mashovNotificator.network.{Response, ResponseCompanion, ReaderCompanion, Request}
import nightra.mashovNotificator.network.logic.{KeyBundle, Key}
import nightra.mashovNotificator.xml.Tag
import argonaut.Argonaut._

case class GradeResponse(subjectName: String, name: String, grade: Int, gradeComment: Option[String], rate: Double, teacherName: String, examDate: String, visibleFrom: String)

case class GradesResponse(grades: Vector[GradeResponse]) extends Response

object GradeResponse extends ReaderCompanion[GradeResponse] {
  implicit def reader = casecodec8(GradeResponse.apply, GradeResponse.unapply)("subjectName", "Name", "grade", "GradeComment", "rate", "teacherName", "eDate", "visiblefrom")
}

object GradesResponse extends ResponseCompanion[GradesResponse] {
  implicit def reader = casecodec1(GradesResponse.apply, GradesResponse.unapply)("allGrades")
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
