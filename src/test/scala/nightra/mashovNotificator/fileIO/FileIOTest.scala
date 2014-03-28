package nightra.mashovNotificator.fileIO

import nightra.mashovNotificator.fileIO.FileIO._
import nightra.mashovNotificator.Test
import java.io.File

class FileIOTest extends Test {
  val content = "Hello world!\r\nHow are you?"


  "Write-Read file" should "have an identity." in {
    val file = testFile("read-write.test")

    val writeRead = for {
      _ <- writeFile(content, file)
      fileContent <- readFile(file)
      _ <- deleteFile(file)
    } yield fileContent

    writeRead.unsafePerformIO() should equal(content)
  }

  "Delete file" should "return true if the file was deleted successfully." in {
    val file = testFile("delete.test")

    val writeDelete = for {
      _ <- writeFile(content, file)
      status <- deleteFile(file)
    } yield status

    writeDelete.unsafePerformIO() should be(true)
  }

  "Delete file" should "return false if there was no file to be deleted." in {
    val file = testFile("non-existing.test")

    val writeDelete = for {
      _ <- deleteFile(file)
      status <- deleteFile(file)
    } yield status

    writeDelete.unsafePerformIO() should be(false)
  }
}
