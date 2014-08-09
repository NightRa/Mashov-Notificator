package nightra.mashovNotificator.fileIO

import nightra.mashovNotificator.fileIO.FileIO._
import nightra.mashovNotificator.Test

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

  "List folder" should "return all the files in a folder." in {
    val folder = testFile("folderTest")
    val file1 = testFile("folderTest/file1.test")
    val file2 = testFile("folderTest/file2.test")

    val writeList = for {
      _ <- writeFile(content, file1)
      _ <- writeFile(content, file2)
      list <- listFolder(folder)
      _ <- deleteFile(file1)
      _ <- deleteFile(file2)
      _ <- deleteFile(folder)
    } yield list


    writeList.unsafePerformIO() should equal(Some(List(file1, file2)))
  }

  "Rename file" should "create a file which is the same as the first one at a different path." in {
    val originalContent = "Original file content."
    val from = testFile("fileFrom.test")
    val to = testFile("fileTo.test")

    val renameRead = for {
      _ <- deleteFile(from)
      _ <- deleteFile(to)
      _ <- writeFile(originalContent, from)
      status <- renameFile(from, to)
      newContent <- readFile(to)
      fromExists <- fileExists(from)
      _ <- deleteFile(from)
      _ <- deleteFile(to)
    } yield (status, newContent, fromExists)

    renameRead.unsafePerformIO() should equal((true, originalContent, false))
  }

  "File Exists" should "return true if the file was just created, and false if it was deleted." in {
    val file = testFile("exists.test")

    val createRemoveExists = for {
      _ <- deleteFile(file)
      nonExistant <- fileExists(file)
      _ <- writeFile(content, file)
      exists <- fileExists(file)
      deleteStatus <- deleteFile(file)
    } yield (nonExistant, exists, deleteStatus)

    createRemoveExists.unsafePerformIO() should equal(false, true, true)
  }

  "Sub file" should "return a file path under a folder" in {
    val folder = testFile("folder")
    val child = "child.test"
    val expected = testFile("folder/child.test")

    subFile(folder, child) should be(expected)
  }

  "Sibling" should "return a file path in the same folder" in {
    val file = testFile("folder/relativeTo.test")
    val siblingName = "sibling.test"
    val expected = testFile("folder/sibling.test")

    sibling(file, siblingName) should be(expected)
  }

  "File name" should "return the name without the parent folders" in {
    val file = testFile("folder/name.test")
    name(file) should be("name.test")
  }
}
