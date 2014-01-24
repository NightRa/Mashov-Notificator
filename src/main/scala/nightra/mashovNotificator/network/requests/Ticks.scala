//Created By Ilan Godik
package nightra.mashovNotificator.network.requests

import nightra.mashovNotificator.xml.Tag
import nightra.mashovNotificator.network.{ResponseCompanion, Response, Request}
import nightra.mashovNotificator.network.readers.TickResponseReader

case class TickResponse(ticks:Long) extends Response

object TickResponse extends ResponseCompanion[TickResponse]{
  implicit val reader = TickResponseReader
}

case object TickRequest extends Request[TickResponse]{
  def toXML = Tag("GetTicks")
}
