//Created By Ilan Godik
package nightra.mashovNotificator.fileIO

import scalaz.effect.IO
import java.io.{PrintWriter, File}
import scala.io.Source

object FileIO {
  def writeFile(content: String, file: File): IO[Unit] = IO {
    val writer = new PrintWriter(file, "UTF-8")
    writer.write(content)
    writer.close()
  }

  def readFileLines(file: File): IO[Seq[String]] = IO {
    Source.fromFile(file).getLines().toStream
  }

  def readFile(file: File): IO[String] = readFileLines(file).map(_.mkString("\r\n"))

  def deleteFile(file: File): IO[Boolean] = IO {
    file.delete()
  }
}
