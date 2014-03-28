package nightra.mashovNotificator

import java.io.File

package object fileIO {
  def testFile(name: String) = new File(s"res/test/$name")
}
