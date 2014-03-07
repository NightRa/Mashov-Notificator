//Created By Ilan Godik
package nightra.mashovNotificator.network.logic

import nightra.mashovNotificator.data.Credentials

case class Key(key: String, credentials: Credentials)

object RequestKeyGenerator {
  def generateKey(credentials: Credentials, session: Int, ticks: Long): Key = {
    import credentials._
    val field1 = ticks + (school.toString + year.toString).toLong
    val halfTicks = ticks / 2
    Key(s"$field1.$halfTicks.$id.$session", credentials)
  }
}
