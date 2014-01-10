//Created By Ilan Godik
package nightra.mashovNotificator.main

import akka.actor.ActorSystem
import scala.concurrent.ExecutionContext

trait Runner {
  implicit def system: ActorSystem
  implicit def executionContent: ExecutionContext
}

trait DefaultRunner extends Runner {
  implicit val system = ActorSystem()
  implicit def executionContent: ExecutionContext = system.dispatcher
}
