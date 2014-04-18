package nightra.mashovNotificator.network

import scala.concurrent.{ExecutionContext, Future}
import scalaz.effect.IO
import scalaz.effect.IO._
import scalaz.Reader
import scala.util.{Success, Failure, Try}

case class Task[A](task: Reader[ExecutionContext, IO[Future[A]]]) {
  def map[B](f: A => B): Task[B] = Task(Reader(implicit executor => task(executor).map(_.map(f))))

  def flatMap[B](f: A => Task[B]): Task[B] = Task(Reader(implicit executor => IO {
    task.run(executor).unsafePerformIO().flatMap(a => f(a).task.run(executor).unsafePerformIO())
  }))

  def filter(p: A => Boolean): Task[A] = Task(Reader(implicit executor => task(executor).map(_.filter(p))))
  def withFilter(p: A => Boolean): Task[A] = filter(p)

  def onComplete(f: Try[A] => IO[Unit]): Task[A] =
    Task(Reader(implicit executor => task(executor).map(future => {
      future.onComplete(t => f(t).unsafePerformIO())
      future
    })))

  def foreach(f: A => IO[Unit]): Task[A] = onSuccess(f)

  def onSuccess(f: A => IO[Unit]): Task[A] = onComplete {
    case Success(value) => f(value)
    case _ => IO(())
  }

  def onFailure(f: Throwable => IO[Unit]): Task[A] = onComplete {
    case Failure(fail) => f(fail)
    case _ => IO(())
  }

  def run(implicit executor: ExecutionContext) = task.run(executor)

  def logAll(f: Try[A] => String): Task[A] = onComplete(t => putStrLn(f(t)))
  def logFailure(f: Throwable => String): Task[A] = onFailure(fail => putStrLn(f(fail)))
  def logSuccess(f: A => String): Task[A] = onSuccess(value => putStrLn(f(value)))
}

object Task {
  def lift[A](f: ExecutionContext => Future[A]): Task[A] =
    Task(Reader(executor => IO {
      f(executor)
    }))
}
