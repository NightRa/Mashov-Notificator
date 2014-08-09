//Created By Ilan Godik
package nightra.mashovNotificator.network

import akka.actor.ActorSystem

import scala.concurrent.ExecutionContext

trait Runner {
  implicit def system: ActorSystem
  implicit def executionContent: ExecutionContext
}

/**
 * Global, Not state
 * I see this as something that may never change,
 * And is considered as basic as allowing function application.
 **/
object Runner extends Runner {
  implicit val system: ActorSystem = ActorSystem()
  implicit def executionContent: ExecutionContext = system.dispatcher
}
