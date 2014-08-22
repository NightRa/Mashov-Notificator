package nightra.mashovNotificator.util

object SideEffectImplicit {

  // If we are doing side effects, let's do it with style!
  implicit class SideEffect[A](a: A) {
    // Execute all the functions on A and return A.
    def side[B](fs: (A => B)*): A = {
      fs.foreach(_.apply(a))
      a
    }
  }

}