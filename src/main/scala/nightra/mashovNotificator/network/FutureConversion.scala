package nightra.mashovNotificator.network

import concurrent.{ExecutionContext, Future => SFuture}
import util.Try

import _root_.scalaz.\/
import _root_.scalaz.concurrent.{Task => ZTask}

object FutureConversion {
  def fromScala[A](future: SFuture[A])(implicit ec: ExecutionContext): ZTask[A] =
    ZTask async (handlerConversion andThen future.onComplete)

  def toTask[A](future: => SFuture[A])(implicit ec: ExecutionContext): ZTask[A] =
    ZTask delay fromScala(future)(ec) flatMap identity

  private def handlerConversion[A]: ((Throwable \/ A) => Unit) => Try[A] => Unit =
    callback => {
      t: Try[A] => \/ fromTryCatch t.get
    } andThen callback

}
