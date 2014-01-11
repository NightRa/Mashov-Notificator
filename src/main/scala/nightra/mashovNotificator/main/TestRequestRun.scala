//Created By Ilan Godik
package nightra.mashovNotificator.main

import nightra.mashovNotificator.requests._
import nightra.mashovNotificator.network.RequestRunner
import scala.concurrent.Future
import nightra.mashovNotificator.requests.LoginRequest

object TestRequestRun extends App {
  self =>
  val runner = new DefaultRunner {}
  val requestRunner = new RequestRunner {
    val runner = self.runner
  }

  import runner._
  import requestRunner._

  val id = 0
  val password = ""
  val school = 0
  val year = 2014

  val loginRequest = LoginRequest(id, password, school, year)
  val tickFuture: Future[TickResponse] = runRequest(TickRequest)
  val sessionFuture: Future[LoginResponse] = runRequest(loginRequest)


  val keyFuture = for {
    TickResponse(ticks) <- tickFuture
    LoginResponse(session, _) <- sessionFuture
  } yield RequestKeyGenerator.generateKey(id, school, year, session, ticks)

  keyFuture.onComplete {
    key =>
      println(key)
      system.shutdown()
  }
}
