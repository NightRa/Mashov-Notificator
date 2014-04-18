//Created By Ilan Godik
package nightra.mashovNotificator.network.unsafe

import scala.concurrent.{ExecutionContext, Future}
import spray.http._
import spray.client.pipelining._
import spray.httpx.unmarshalling._
import nightra.mashovNotificator.main.Runner
import nightra.mashovNotificator.network.{Task, Request, Response}
import nightra.mashovNotificator.network.Request._
import argonaut.DecodeJson
import argonaut.Argonaut._
import spray.http.HttpRequest
import spray.http.HttpResponse
import akka.actor.ActorSystem

trait RequestRunner {
  implicit val actorSystem: ActorSystem

  def setContentType: ContentType => HttpResponse => HttpResponse =
    contentType => resp => resp.withEntity(HttpEntity(contentType, resp.entity.data))

  def soapPipeline[Resp <: Response : Unmarshaller]: HttpRequest => Task[Resp] =
    request => Task.lift {
      implicit executor =>
        sendReceive
          .~>(setContentType(ContentTypes.`application/json`)
          .~>(unmarshal[Resp]))
          .apply(request)
    }


  def runRequest[Resp <: Response : DecodeJson](req: Request[Resp]): Task[Resp] = {
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
