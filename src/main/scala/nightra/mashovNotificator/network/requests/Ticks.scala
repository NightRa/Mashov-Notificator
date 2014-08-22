//Created By Ilan Godik
package nightra.mashovNotificator.network.requests

import nightra.mashovNotificator.xml.Tag
import nightra.mashovNotificator.network.{ResponseCompanion, Response, Request}
import argonaut._

case class TickResponse(ticks: Long) extends Response

object TickResponse extends ResponseCompanion[TickResponse] {
  /**
   * {"ticks":[{"ticks":"635304156250170000"}]} -> TickResponse(635304156250170000)
   **/
  implicit def reader = DecodeJson(_.downField("ticks").downArray.downField("ticks").as[Long].map(TickResponse.apply))
}

case object TickRequest extends Request[TickResponse] {
  def toXML = Tag("GetTicks")
  def name: String = "Tick"
}
