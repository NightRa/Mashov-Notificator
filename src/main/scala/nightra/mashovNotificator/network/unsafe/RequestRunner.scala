//Created By Ilan Godik
package nightra.mashovNotificator.network.unsafe

import scala.concurrent.Future
import spray.http._
import spray.json._
import spray.httpx.SprayJsonSupport._
import spray.client.pipelining._
import spray.httpx.unmarshalling.FromResponseUnmarshaller
import nightra.mashovNotificator.main.Runner
import nightra.mashovNotificator.network.{Request, Response}
import nightra.mashovNotificator.network.Request._

trait RequestRunner {
  val runner: Runner
  import runner._

  def setContentType: ContentType => HttpResponse => HttpResponse =
    contentType => resp => resp.withEntity(HttpEntity(contentType, resp.entity.data)) 
  
  def soapPipeline[Resp <: Response : FromResponseUnmarshaller]: HttpRequest => Future[Resp] = (
    sendReceive
      ~> setContentType(ContentTypes.`application/json`)
      ~> unmarshal[Resp]
    )

  def runRequest[Resp <: Response : RootJsonReader](req:Request[Resp]): Future[Resp] = {
    val pipeline = soapPipeline[Resp]
    val request = prepareRequest(req)
    pipeline(request)
  }
}
