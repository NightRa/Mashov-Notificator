//Created By Ilan Godik
package nightra.mashovNotificator.main

import akka.actor.ActorSystem
import scala.concurrent.ExecutionContext

trait Runner {
  implicit def system: ActorSystem
  implicit def executionContent: ExecutionContext
}

class DefaultRunner extends Runner {
  implicit val system: ActorSystem = ActorSystem()
  implicit def executionContent: ExecutionContext = system.dispatcher
}
