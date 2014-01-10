//Created By Ilan Godik
package nightra.mashovNotificator.main

import nightra.mashovNotificator.requests.LoginRequest
import nightra.mashovNotificator.network.RequestRunner

object TestRequestRun extends App{
  val runner = new DefaultRunner {}
  val requestRunner = new RequestRunner {
    val runner = TestRequestRun.this.runner
  }

  import runner._
  import requestRunner._

  val request = LoginRequest("316315332","v2mv7h",340208,2014)
  val response = runRequest(request)

  response.onComplete{
    res =>
      println(res)
      system.shutdown()
  }
}
