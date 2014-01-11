//Created By Ilan Godik
package nightra.mashovNotificator.requests

import nightra.mashovNotificator.xml.Tag
import spray.json.{JsValue, RootJsonReader}

case class LoginResponse(session: Int, userType: Int) extends Response

object LoginResponse {
  import spray.json._
  import spray.json.DefaultJsonProtocol._
  import spray.json.lenses.JsonLenses._
  implicit object LoginResponseJsonReader extends RootJsonReader[LoginResponse]{
    def read(json: JsValue): LoginResponse = {
      val rootLens = 'loginInfo / element(0)
      val sessionLens = rootLens / 'session
      val userTypeLens = rootLens / 'usertype
      val session = json.extract[String](sessionLens).toInt
      val userType = json.extract[String](userTypeLens).toInt
      LoginResponse(session,userType)
    }
  }
}

case class LoginRequest(id: Int, password: String, school: Int, year: Int) extends Request[LoginResponse] {
  def toXML =
    Tag("Login", children = Seq(
      Tag("id", id.toString),
      Tag("pass", password),
      Tag("CurrentYearS", school.toString + year.toString),
      Tag("ver", "202"),
      Tag("comp", "android_id"),
      Tag("Code", "87-4B-D3-81-D4-D7-10-61-53-DA-90-9C-56-C7-07-84-70-CD-39-F6")
    ))
}
