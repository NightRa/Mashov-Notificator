//Created By Ilan Godik
package nightra.mashovNotificator.main

import nightra.mashovNotificator.requests.LoginRequest
import nightra.mashovNotificator.network.RequestRunner

object TestRequestRun extends App{ self =>
  val runner = new DefaultRunner {}
  val requestRunner = new RequestRunner {
    val runner = self.runner
  }

  import runner._
  import requestRunner._

  val id = ""
  val password = ""
  val school = 0
  val year = 2014

  val request = LoginRequest(id,password,school,year)
  val response = runRequest(request)

  response.onComplete{
    res =>
      println(res)
      system.shutdown()
  }
}
