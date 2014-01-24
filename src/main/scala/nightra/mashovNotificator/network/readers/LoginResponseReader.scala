//Created By Ilan Godik
package nightra.mashovNotificator.network.readers

import nightra.mashovNotificator.network.requests.LoginResponse
import spray.json._
import spray.json.DefaultJsonProtocol._
import spray.json.lenses.JsonLenses._

object LoginResponseReader extends RootJsonReader[LoginResponse]{
  def read(json: JsValue): LoginResponse = {
    val root = 'loginInfo / element(0)
    val session = json.extract[String](root / 'session).toInt
    val userType = json.extract[String](root / 'usertype).toInt
    LoginResponse(session, userType)
  }
}
