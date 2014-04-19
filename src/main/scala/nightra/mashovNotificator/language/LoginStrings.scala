package nightra.mashovNotificator.language

trait LoginStrings {
  val id: String
  val pass: String
  val loginHeader: String
  val loginButton: String
  val loggingIn: String
}

object DefaultLoginStrings extends LoginStrings {
  val id = "תעודת זהות"
  val pass = "סיסמא"
  val loginHeader = "התחברות"
  val loginButton = "התחבר"
  val loggingIn: String = "מתחבר"
}
