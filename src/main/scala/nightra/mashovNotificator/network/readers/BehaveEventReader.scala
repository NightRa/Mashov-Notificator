//Created By Ilan Godik
package nightra.mashovNotificator.network.readers

import nightra.mashovNotificator.network.requests.BehaveEvent
import spray.json._
import spray.json.DefaultJsonProtocol._
import spray.json.lenses.JsonLenses._

object BehaveEventReader extends JsonReader[BehaveEvent]{


  def read(json: JsValue):BehaveEvent = {
    val studentID = json.extract[Int]("studentId")
    val lessonID = json.extract[Int]("lessonId")
    val eventCode = json.extract[Int]("eventCode")
    val justified = json.extract[Int]("justified")
    val reporterID = json.extract[Int]("reporterid")
    val groupID = json.extract[Int]("groupid")
    val lesson = json.extract[Int]("lesson")
    val lessonDate = json.extract[String]("lessondate")
    val name = json.extract[String]("name")
    val justification = json.extract[String]("justification")
    val teacherName = json.extract[String]("teacherName")
    val subjectName = json.extract[String]("subjectName")
    val remarkText = json.extract[String]("remarktext")
    BehaveEvent(studentID,lessonID,eventCode,justified,reporterID,groupID,lesson,lessonDate,name,justification,teacherName, subjectName, remarkText)
  }
}
