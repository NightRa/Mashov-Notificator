//Created By Ilan Godik
package nightra.mashovNotificator.network

import scala.concurrent.Future
import spray.http._
import spray.json._
import spray.httpx.SprayJsonSupport._
import spray.client.pipelining._

import nightra.mashovNotificator.requests.{Response, Request}
import Request._
import nightra.mashovNotificator.main.Runner

trait RequestRunner {
  val runner: Runner
  import runner._

  def setContentType: ContentType => HttpResponse => HttpResponse =
    contentType => resp => resp.withEntity(HttpEntity(contentType, resp.entity.data)) 
  
  def soapPipeline[Resp <: Response : RootJsonReader]: HttpRequest => Future[Resp] = (
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
