//Created By Ilan Godik
package nightra.mashovNotificator.main

import nightra.mashovNotificator.network.requests._
import scala.concurrent.Future
import nightra.mashovNotificator.network.requests.BehaveEventsRequest
import nightra.mashovNotificator.network.requests.LoginRequest
import nightra.mashovNotificator.network.logic.RequestKeyGenerator
import nightra.mashovNotificator.network.unsafe.RequestRunner

object TestRequestRun extends App {
  val mainRunner = new DefaultRunner {}
  val requestRunner = new RequestRunner {
    val runner = mainRunner
  }

  import mainRunner._
  import requestRunner._

  val id = 316315332
  val password = "v2mv7h"
  val school = 340208
  val year = 2014

  val loginRequest = LoginRequest(id, password, school, year)

  val startTime = System.currentTimeMillis()
  println(s"Starting Requests at:0")

  val tickFuture: Future[TickResponse] = runRequest(TickRequest)
  val sessionFuture: Future[LoginResponse] = runRequest(loginRequest)

  val requestsRun = System.currentTimeMillis()

  val keyFuture = for {
    TickResponse(ticks) <- tickFuture
    LoginResponse(session, _) <- sessionFuture
  } yield RequestKeyGenerator.generateKey(id, school, year, session, ticks)

  val behaveEventsFuture = for {
    key <- keyFuture
    behaveRequest = BehaveEventsRequest(id, key)
    _ = println(s"Initiated Behave request at:$timeFromStart")
    behaveEvents <- runRequest(behaveRequest)
  } yield behaveEvents

  val gradesFuture = for {
    key <- keyFuture
    gradesRequest = GradesRequest(id, key)
    _ = println(s"Initiated Grades request at:$timeFromStart")
    gradesEvents <- runRequest(gradesRequest)
  } yield gradesEvents
  
  println(s"Requests initiated at:${requestsRun - startTime}")

  tickFuture.onComplete(const(println(statusMessage("ticks"))))

  sessionFuture.onComplete(const(println(statusMessage("session"))))

  keyFuture.onComplete {
    key =>
      println(statusMessage("key"))
      println(key)
  }

  behaveEventsFuture.onComplete {
    behaveEvents =>
      println(statusMessage("Behave Events"))
      println(behaveEvents.get.events.mkString("\r\n"))
  }

  gradesFuture.onComplete {
    grades =>
      println(statusMessage("Grades Events"))
      println(grades.get.grades.mkString("\r\n"))
  }

  for{
    _ <- behaveEventsFuture
    _ <- gradesFuture
  }{
    system.shutdown()
  }

  def timeFromStart = System.currentTimeMillis() - startTime
  def statusMessage(topic: String) = s"Got $topic at:$timeFromStart"
  def const[A, B](b: => B): (A => B) = a => b
}
