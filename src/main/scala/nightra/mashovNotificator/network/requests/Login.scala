//Created By Ilan Godik
package nightra.mashovNotificator.network.requests

import nightra.mashovNotificator.xml.Tag
import nightra.mashovNotificator.network.{ResponseCompanion, Response, Request}
import nightra.mashovNotificator.network.readers.LoginResponseReader

case class LoginResponse(session: Int, userType: Int) extends Response

object LoginResponse extends ResponseCompanion[LoginResponse]{
  implicit val reader = LoginResponseReader
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