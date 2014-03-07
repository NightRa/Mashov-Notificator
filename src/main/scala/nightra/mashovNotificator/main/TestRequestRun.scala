//Created By Ilan Godik
package nightra.mashovNotificator.main

import nightra.{mashovNotificator => m}
import m.network.requests._
import nightra.mashovNotificator.network.logic.{Key, RequestKeyGenerator}
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

  def getKey(credentials: Credentials)(ticks: Long)(session: Int) =
    RequestKeyGenerator.generateKeyBundle(credentials, session, ticks)

  def requestKey(credentials: Credentials) = {
    val tickFuture: Future[TickResponse] = runRequest(TickRequest)
    val sessionFuture: Future[LoginResponse] = runRequest(LoginRequest(credentials))

    for {
      TickResponse(ticks) <- tickFuture
      LoginResponse(session, _) <- sessionFuture
    } yield getKey(credentials)(ticks)(session)
  }

  // TODO: Separate behavior events request and grades request.
  //def requestGrades(key: Key) =

  def getData(credentials: Credentials): (Future[GradesResponse], Future[BehaveEventsResponse]) = {
    val keyFuture = requestKey(credentials)

    val behaveEventsFuture = for {
      keyBundle <- keyFuture
      behaveRequest = BehaveEventsRequest(keyBundle.credentials.id, keyBundle.key)
      behaveEvents <- runRequest(behaveRequest)
    } yield behaveEvents

    val gradesFuture = for {
      keyBundle <- keyFuture
      gradesRequest = GradesRequest(keyBundle.credentials.id, keyBundle.key)
      gradesEvents <- runRequest(gradesRequest)
    } yield gradesEvents

    (gradesFuture, behaveEventsFuture)
  }
}
