//Created By Ilan Godik
package nightra.mashovNotificator.network

import nightra.mashovNotificator.data.Credentials
import nightra.mashovNotificator.model.{BehaviorEvent, DomainMorphism, Grade}
import nightra.mashovNotificator.network.logic.{KeyBundle, RequestKeyGenerator}
import nightra.mashovNotificator.network.requests._
import nightra.mashovNotificator.network.unsafe.RequestsLow

import scalaz.Nondeterminism
import scalaz.concurrent.Task

object Requests {

  import RequestsLow._

  def login(credentials: Credentials, log: Boolean): Task[KeyBundle] = {
    Nondeterminism[Task].mapBoth(

      runRequestLog(TickRequest, log),
      runRequestLog(LoginRequest(credentials), log)) {

      case (TickResponse(ticks), LoginResponse(session, _)) =>
        RequestKeyGenerator.generateKeyBundle(credentials, session, ticks)
    }
  }

  def requestGrades(keyBundle: KeyBundle, log: Boolean): Task[Seq[Grade]] =
    runRequestLog(GradesRequest(keyBundle), log)
      .map(DomainMorphism[GradesResponse, Seq[Grade]])

  def requestBehaviorEvents(keyBundle: KeyBundle, log: Boolean): Task[Seq[BehaviorEvent]] =
    runRequestLog(BehaveEventsRequest(keyBundle), log)
      .map(DomainMorphism[BehaveEventsResponse, Seq[BehaviorEvent]])


  def requestGradesAndBehaviorWithLogin(credentials: Credentials, log: Boolean): Task[(Seq[Grade], Seq[BehaviorEvent])] = {
    login(credentials, log).flatMap {
      keyBundle =>

        Nondeterminism[Task].both(
          requestGrades(keyBundle, log),
          requestBehaviorEvents(keyBundle, log)
        )
    }
  }
}
