//Created By Ilan Godik
package nightra.mashovNotificator.network.readers

import nightra.mashovNotificator.network.requests.{BehaveEvent, BehaveEventsResponse}
import spray.json._
import spray.json.DefaultJsonProtocol._
import spray.json.lenses.JsonLenses._

object BehaveEventsReader extends RootJsonReader[BehaveEventsResponse]{
  def read(json: JsValue):BehaveEventsResponse =
    BehaveEventsResponse(json.extract[BehaveEvent]('tblStudentAchva / elements))
}
