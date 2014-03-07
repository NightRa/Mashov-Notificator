//Created By Ilan Godik
package nightra.mashovNotificator.network.logic

import nightra.mashovNotificator.data.Credentials

case class KeyBundle(credentials: Credentials, key: Key)

case class Key(key: String) extends AnyVal

object RequestKeyGenerator {
  def generateKeyBundle(credentials: Credentials, session: Int, ticks: Long): KeyBundle = {
    import credentials._
    val key = generateKey(id, school, year, session, ticks)
    KeyBundle(credentials, key)
  }

  def generateKey(id: Int, school: Int, year: Int, session: Int, ticks: Long): Key = {
    val field1 = ticks + (school.toString + year.toString).toLong
    val halfTicks = ticks / 2
    Key(s"$field1.$halfTicks.$id.$session")
  }
}
