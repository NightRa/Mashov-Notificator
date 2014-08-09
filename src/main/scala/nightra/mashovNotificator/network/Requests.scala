//Created By Ilan Godik
package nightra.mashovNotificator.network

import nightra.mashovNotificator.data.Credentials
import nightra.mashovNotificator.network.logic.{KeyBundle, RequestKeyGenerator}
import nightra.mashovNotificator.network.requests._
import nightra.mashovNotificator.network.unsafe.RequestsLow

import scalaz.Nondeterminism
import scalaz.concurrent.Task

object Requests {

  import RequestsLow._

  private def getKey(credentials: Credentials)(ticks: Long)(session: Int) =
    RequestKeyGenerator.generateKeyBundle(credentials, session, ticks)

  def requestKey(credentials: Credentials): Task[KeyBundle] = {
    Nondeterminism[Task].mapBoth(

      runRequest(TickRequest),
      runRequest(LoginRequest(credentials))) {

      case (TickResponse(ticks), LoginResponse(session, _)) =>
        getKey(credentials)(ticks)(session)
    }
  }

  def requestGrades(keyBundle: KeyBundle): Task[GradesResponse] =
    runRequest(GradesRequest(keyBundle))
  def requestBehaviorEvents(keyBundle: KeyBundle): Task[BehaveEventsResponse] =
    runRequest(BehaveEventsRequest(keyBundle))
}
