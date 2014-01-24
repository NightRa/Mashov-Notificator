//Created By Ilan Godik
package nightra.mashovNotificator.network.readers

import nightra.mashovNotificator.network.requests.TickResponse
import spray.json._
import spray.json.DefaultJsonProtocol._
import spray.json.lenses.JsonLenses._

object TickResponseReader extends RootJsonReader[TickResponse]{
  def read(json: JsValue): TickResponse =
    TickResponse(json.extract[String]('ticks / element(0) / 'ticks).toLong)
}
