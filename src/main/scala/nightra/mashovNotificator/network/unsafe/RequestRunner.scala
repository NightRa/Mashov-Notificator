//Created By Ilan Godik
package nightra.mashovNotificator.network.unsafe

import scala.concurrent.Future
import spray.http._
import spray.client.pipelining._
import spray.httpx.unmarshalling._
import nightra.mashovNotificator.main.Runner
import nightra.mashovNotificator.network.{Request, Response}
import nightra.mashovNotificator.network.Request._
import argonaut.DecodeJson
import argonaut.Argonaut._
import spray.http.HttpRequest
import spray.http.HttpResponse

trait RequestRunner {
  val runner: Runner

  import runner._

  def setContentType: ContentType => HttpResponse => HttpResponse =
    contentType => resp => resp.withEntity(HttpEntity(contentType, resp.entity.data))

  def soapPipeline[Resp <: Response : Unmarshaller]: HttpRequest => Future[Resp] = (
    sendReceive
      ~> setContentType(ContentTypes.`application/json`)
      ~> unmarshal[Resp]
    )

  def runRequest[Resp <: Response : DecodeJson](req: Request[Resp]): Future[Resp] = {
    val pipeline = soapPipeline[Resp](argonautJsonUnmarshaller[Resp])
    val request = prepareRequest(req)
    pipeline(request)
  }

  def argonautJsonUnmarshaller[T: DecodeJson] =
    new Unmarshaller[T] {
      def apply(entity: HttpEntity): Either[DeserializationError, T] = entity match {
        case x: HttpEntity.NonEmpty ⇒
          x.asString(defaultCharset = HttpCharsets.`UTF-8`).decode[T]
            .fold(err => Left(
            err.fold(message => new MalformedContent(message),
              history => new MalformedContent(history.toString))),
              Right(_))
        case _ => Left(ContentExpected)
      }
    }
}
