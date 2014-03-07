//Created By Ilan Godik
package nightra.mashovNotificator.util

import scalaz.{Order, Show}
import scalaz.syntax.semigroup._
import Order.orderBy

case class Date(day: Int, month: Int, year: Int) {
  override def toString = f"$day%02d/$month%02d/$year%04d"
}

object Date {
  val day: Date => Int = _.day
  val month: Date => Int = _.month
  val year: Date => Int = _.year

  implicit val dateShow: Show[Date] = Show.showFromToString
  implicit val dateOrder: Order[Date] = orderBy(year) |+| orderBy(month) |+| orderBy(day)

  // TODO: Implement date parsing.
  // 2014-01-09T00:00:00 -> Date(09,01,2014)
  def parseDate: String => Date =
    _ => Date(20, 2, 2014)

}
