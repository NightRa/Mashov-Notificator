//Created By Ilan Godik
package nightra.mashovNotificator.util

import scalaz._
import scalaz.syntax.semigroup._
import Order.orderBy
import atto._
import Atto._
import argonaut.Argonaut.casecodec3

case class Date(day: Int, month: Int, year: Int) {
  override def toString = f"$day%02d/$month%02d/$year%04d"
}

object Date {
  val day: Date => Int = _.day
  val month: Date => Int = _.month
  val year: Date => Int = _.year

  implicit val dateShow: Show[Date] = Show.showFromToString
  implicit val dateOrder: Order[Date] = orderBy(year) |+| orderBy(month) |+| orderBy(day)

  implicit val jsonCodec = casecodec3(Date.apply, Date.unapply)("day", "month", "year")



  // 2014-01-09T00:00:00 -> Right(Date(09,01,2014))
  // TODO: (Date parsing): ABSOLUTELY BAD. SHOULD PASS ERROR INSTEAD OF ASSUMING FORMAT WAS CORRECT.
  def parseDate: String => Date = in =>
    dateParser.parseOnly(in).option match {
      case Some(x) => x
      case None => throw new IllegalArgumentException(s"Invalid date: $in")
    }

  def dateParser = (for {
    year <- int <~ char('-')
    month <- int <~ char('-')
    day <- int
  } yield Date(day, month, year)).filter(isCorrectDate)

  // TODO: Date validation is very simplistic
  def isCorrectDate(date: Date): Boolean =
    date.month >= 1 && date.month <= 12 && date.day >= 1 && date.day <= 31


}
