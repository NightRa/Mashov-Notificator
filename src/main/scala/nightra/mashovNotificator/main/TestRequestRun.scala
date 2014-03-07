//Created By Ilan Godik
package nightra.mashovNotificator.main

import nightra.{mashovNotificator => m}
import m.network.requests._
import nightra.mashovNotificator.network.logic.{KeyBundle, Key, RequestKeyGenerator}
import m.network.unsafe.RequestRunner
import m.data.Credentials
import scala.concurrent.Future

class TestRequestRun {
  val mainRunner = new DefaultRunner
  val requestRunner = new RequestRunner {
    val runner = mainRunner
  }

  import mainRunner._
  import requestRunner._

  val id = ???
  val password = ???
  val school = ???
  val year = ???

  val credentials = Credentials(id, password, school, year)

  private def getKey(credentials: Credentials)(ticks: Long)(session: Int) =
    RequestKeyGenerator.generateKeyBundle(credentials, session, ticks)

  def requestKey(credentials: Credentials) = {
    val tickFuture: Future[TickResponse] = runRequest(TickRequest)
    val sessionFuture: Future[LoginResponse] = runRequest(LoginRequest(credentials))

    for {
      TickResponse(ticks) <- tickFuture
      LoginResponse(session, _) <- sessionFuture
    } yield getKey(credentials)(ticks)(session)
  }

  def requestGrades(keyBundle: KeyBundle) = runRequest(GradesRequest(keyBundle))
  def requestBehaviorEvents(keyBundle: KeyBundle) = runRequest(BehaveEventsRequest(keyBundle))
}
