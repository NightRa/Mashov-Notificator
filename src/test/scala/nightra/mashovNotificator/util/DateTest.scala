//Created By Ilan Godik
package nightra.mashovNotificator.util

import org.scalatest.{Matchers, FlatSpec}

class DateTest extends FlatSpec with Matchers {
  "Date parsing" should "parse \"2014-01-09T00:00:00\" to Date(09,01,2014)" in {
    Date.parseDate("2014-01-09T00:00:00") should equal(Some(Date(9, 1, 2014)))
  }

  it should "not parse \"2014--3-32\"" in {
    Date.parseDate("2014--3-32") should equal(None)
  }
}
