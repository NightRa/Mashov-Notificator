//Created By Ilan Godik
package nightra.mashovNotificator.fileIO

import scalaz.effect.IO
import java.io.{PrintWriter, File}
import scala.io.Source
import scalaz.\/
import argonaut.{DecodeJson, Parse}

object FileIO {
  def writeFile(content: String, file: File): IO[Unit] = IO {
    file.getParentFile.mkdirs()
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

  def listFolder(folder: File): IO[Option[List[File]]] = IO {
    Option(folder.listFiles()).map(_.toList)
  }

  def renameFile(from: File, to: File): IO[Boolean] = IO {
    from.renameTo(to)
  }

  def fileExists(file: File): IO[Boolean] = IO {
    file.exists()
  }

  def subFile(folder: File, file: String): File = folder.toPath.resolve(file).toFile

  def sibling(file: File, sibling: String): File = file.toPath.resolveSibling(sibling).toFile

  def readJson[A: DecodeJson](file: File): IO[String \/ A] =
    readFile(file).map(Parse.decodeEither[A])
}
