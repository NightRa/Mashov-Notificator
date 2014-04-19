package nightra.mashovNotificator.gui

object SideEffectImplicit {

  // If we are doing side effects, let's do it with style!
  implicit class SideEffect[A](a: A) {
    def side[B](fs: (A => B)*) = {
      fs.foreach(_.apply(a))
      a
    }
  }

}
