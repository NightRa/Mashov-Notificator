//Created By Ilan Godik
package nightra.mashovNotificator.requests

object RequestKeyGenerator {
  def generateKey(id:Int,school:Int,year:Int,session:Int,ticks:Long) = {
    val field1 = ticks + (school.toString + year.toString).toLong
    val halfTicks = ticks / 2
    s"$field1.$halfTicks.$id.$session"
  }
}
