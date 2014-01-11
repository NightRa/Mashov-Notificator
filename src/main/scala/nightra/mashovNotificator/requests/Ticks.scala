//Created By Ilan Godik
package nightra.mashovNotificator.requests

import nightra.mashovNotificator.xml.Tag

case class TickResponse(ticks:Long) extends Response

object TickResponse {
  import spray.json._
  import spray.json.DefaultJsonProtocol._
  import spray.json.lenses.JsonLenses._

  implicit object TickResponseJsonReader extends RootJsonReader[TickResponse]{
    def read(json: JsValue): TickResponse =
      TickResponse(json.extract[String]('ticks / element(0) / 'ticks).toLong)
  }
}

case object TickRequest extends Request[TickResponse]{
  def toXML = Tag("GetTicks","")
}
