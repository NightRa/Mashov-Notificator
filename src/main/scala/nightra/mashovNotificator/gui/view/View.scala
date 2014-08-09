package nightra.mashovNotificator.gui.view

import scalafx.scene.Node
import scalaz.effect.IO

sealed trait ViewAction

case class SetNodes(nodes: Seq[Node]) extends ViewAction

trait View {
  def applyViewAction(action: ViewAction): IO[Unit]
}