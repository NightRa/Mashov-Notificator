//Created By Ilan Godik
package nightra.mashovNotificator.network.unsafe

import argonaut.Argonaut._
import argonaut.DecodeJson
import nightra.mashovNotificator.network.Request._
import nightra.mashovNotificator.network.{Runner, FutureConversion, Request, Response}
import spray.client.pipelining._
import spray.http._
import spray.httpx.unmarshalling._

import scala.concurrent.Future
import scalaz.concurrent.Task
import scalaz.syntax.bind._

object RequestsLow {

  import Runner._

  def setContentType: ContentType => HttpResponse => HttpResponse =
    contentType => resp => resp.withEntity(HttpEntity(contentType, resp.entity.data))

  def soapPipeline[Resp <: Response : Unmarshaller]: HttpRequest => Future[Resp] = (
    sendReceive
      ~> setContentType(ContentTypes.`application/json`)
      ~> unmarshal[Resp]
    )

  def runRequest[Resp <: Response : DecodeJson](req: Request[Resp]): Task[Resp] = {
    val pipeline = soapPipeline[Resp](argonautJsonUnmarshaller[Resp])
    val request = prepareRequest(req)
    FutureConversion.toTask(
      pipeline(request)
    )
  }

  def runRequestLog[Resp <: Response : DecodeJson](req: Request[Resp], log: Boolean): Task[Resp] = {
    val task = runRequest(req)
    if (log)
      Task.delay(println(s"Starting ${req.name} task.")) >> task.onFinish(_ => Task.delay(println(s"Finished ${req.name} task.")))
    else
      task
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
