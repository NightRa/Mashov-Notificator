package nightra.mashovNotificator.util

import scalaz.{\/-, -\/, \/}
import scalaz.concurrent.{Future, Task}
import scalaz.effect.IO
import scalaz.syntax.bind._
import scala.language.implicitConversions

object IOTaskFuture {
  // All of this feels so ugly.

  implicit def IOToTask[A](io: IO[A]): Task[A] = Task.delay(io.unsafePerformIO())

  def taskToFuture[A, B](task: Task[A])(f: (Throwable \/ A) => Future[B]): Future[B] = task.get.flatMap(f)
  def handleTask[A](task: Task[A], onFail: A): Future[A] = taskToFuture(task) {
    case -\/(exception) => Future.delay(exception.printStackTrace()) >> Future.now(onFail)
    case \/-(a) => Future.now(a)
  }
  def futureToIO[A](future: Future[A], f: A => Unit): IO[Unit] = IO {
    future.runAsync(f)
  }

  def taskToIOHandleAndIgnore[A](task:Task[A]): IO[Unit] = futureToIO[Unit](handleTask(task.map(_ => ()),()), _ => ())
}
