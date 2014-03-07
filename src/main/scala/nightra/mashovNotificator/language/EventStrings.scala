//Created By Ilan Godik
package nightra.mashovNotificator.language

trait EventStrings {
  def subject: String
  def topic: String
  def grade: String
  def date: String
  def `type`: String
  def justification: String
}

object DefaultEventStrings extends EventStrings {
  def subject = "מקצוע"
  def topic = "נושא"
  def grade = "ציון"
  def date = "תאריך"
  def `type` = "סוג"
  def justification = "הצדקה"
}
