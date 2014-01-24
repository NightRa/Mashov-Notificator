//Created By Ilan Godik
package nightra.mashovNotificator.network.readers

import nightra.mashovNotificator.network.requests.GradeResponse
import spray.json._
import spray.json.DefaultJsonProtocol._
import spray.json.lenses.JsonLenses._

object GradeReader extends JsonReader[GradeResponse]{
  def read(json: JsValue):GradeResponse = {
    val subjectName = json.extract[String]("subjectName")
    val name = json.extract[String]("Name")
    val grade = json.extract[Int]("grade")
    val gradeComment = json.extract[Option[String]]("GradeComment")
    val rate = json.extract[Double]("rate")
    val teacherName = json.extract[String]("teacherName")
    val eDate = json.extract[String]("eDate")
    val visibleFrom = json.extract[String]("visiblefrom")
    GradeResponse(subjectName,name,grade,gradeComment,rate,teacherName,eDate,visibleFrom)
  }
}
