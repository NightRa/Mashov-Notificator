package nightra.mashovNotificator.gui.view.mainScene

import scalafx.scene.Node

sealed trait MainSceneViewAction
case class SetNodes(nodes: Seq[Node]) extends MainSceneViewAction