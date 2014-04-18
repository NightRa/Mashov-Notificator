package nightra.mashovNotificator.tasks

import nightra.mashovNotificator.network.Task
import nightra.mashovNotificator.network.requests._
import nightra.mashovNotificator.network.unsafe.RequestRunner
import nightra.mashovNotificator.model.{BehaviorEvent, Grade, DomainMorphism}
import nightra.mashovNotificator.network.logic.RequestKeyGenerator
import nightra.mashovNotificator.data.Credentials
import nightra.mashovNotificator.network.logic.KeyBundle

trait Requests {
  val requestRunner: RequestRunner

  import requestRunner._

  def login(credentials: Credentials): Task[KeyBundle] = {
    val tickFuture: Task[TickResponse] = runRequest(TickRequest)
    val sessionFuture: Task[LoginResponse] = runRequest(LoginRequest(credentials))

    for {
      TickResponse(ticks) <- tickFuture
      LoginResponse(session, _) <- sessionFuture
    } yield RequestKeyGenerator.generateKeyBundle(credentials, session, ticks)
  }

  def requestGrades(keyBundle: KeyBundle): Task[Seq[Grade]] =
    runRequest(GradesRequest(keyBundle))
      .map(DomainMorphism[GradesResponse, Seq[Grade]])

  def requestBehaviorEvents(keyBundle: KeyBundle): Task[Seq[BehaviorEvent]] =
    runRequest(BehaveEventsRequest(keyBundle))
      .map(DomainMorphism[BehaveEventsResponse, Seq[BehaviorEvent]])


  def requestGradesAndBehaviorWithLogin(credentials: Credentials): Task[(Seq[Grade], Seq[BehaviorEvent])] =
    for {
      keyBundle <- login(credentials)
      grades <- requestGrades(keyBundle)
      behaviorEvents <- requestBehaviorEvents(keyBundle)
    } yield (grades, behaviorEvents)

}
