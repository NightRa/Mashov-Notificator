//Created By Ilan Godik
package nightra.mashovNotificator.requests

import nightra.mashovNotificator.xml.Tag
import spray.json.{JsonReader, JsValue, RootJsonReader}

case class BehaveEventsRequest(id:Int,key:Key) extends Request[BehaveEventsResponse]{
  def toXML =
    Tag("getStudentAchva",children = Seq(
      Tag("id",id.toString),
      Tag("CurrentYearS",key.key),
      Tag("startDate","01/01/2001"),
      Tag("endDate","01/01/2030")
    ))
}

case class BehaveEvent(studentID:Int,lessonID:Int,eventCode:Int,justified:Int,reporterID:Int,groupID:Int,lesson:Int,
                       lessonDate:String,name:String,justification:String,teacherName:String, subjectName: String, remarkText:String)

object BehaveEvent{
  import spray.json._
  import spray.json.DefaultJsonProtocol._
  import spray.json.lenses.JsonLenses._

  implicit object reader extends JsonReader[BehaveEvent]{
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
}


case class BehaveEventsResponse(events:Seq[BehaveEvent]) extends Response

object BehaveEventsResponse extends ResponseCompanion[BehaveEventsResponse]{
  import spray.json._
  import spray.json.DefaultJsonProtocol._
  import spray.json.lenses.JsonLenses._

  implicit object reader extends RootJsonReader[BehaveEventsResponse] {
    def read(json: JsValue):BehaveEventsResponse =
      BehaveEventsResponse(json.extract[BehaveEvent]('tblStudentAchva / elements))

  }
}
