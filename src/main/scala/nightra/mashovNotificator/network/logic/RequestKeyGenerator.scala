//Created By Ilan Godik
package nightra.mashovNotificator.network.logic

case class Key(key:String) extends AnyVal

object RequestKeyGenerator {
  def generateKey(id:Int,school:Int,year:Int,session:Int,ticks:Long): Key = {
    val field1 = ticks + (school.toString + year.toString).toLong
    val halfTicks = ticks / 2
    Key(s"$field1.$halfTicks.$id.$session")
  }
}
