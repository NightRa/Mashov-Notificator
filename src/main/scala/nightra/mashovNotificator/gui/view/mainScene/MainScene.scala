package nightra.mashovNotificator.gui.view.mainScene

import nightra.mashovNotificator.gui.description.MainSceneDescription
import nightra.mashovNotificator.gui.view.View

import scalafx.application.Platform
import scalafx.beans.property.ObjectProperty
import scalafx.scene.{Node, Scene}
import scalaz.effect.IO

class MainScene(initial: MainSceneViewAction) extends Scene(800, 600) with View[MainSceneDescription.type] {
  sceneSelf =>

  // --------------------------------------PROPERTIES-------------------------------------------------------------------

  val eventNodesProperty: ObjectProperty[Seq[Node]] = ObjectProperty(Seq.empty)

  // --------------------------------------INITIALIZATION---------------------------------------------------------------

  content = new MainSceneContent(eventNodesProperty) {

    prefHeight <== sceneSelf.height
    prefWidth <== sceneSelf.width
  }

  applyViewAction(initial)

  // --------------------------------------FUNCTIONS--------------------------------------------------------------------

  def setNodes(nodes: Seq[Node]): IO[Unit] = IO {
    Platform.runLater(eventNodesProperty.value = nodes)
  }

  def applyViewAction(action: MainSceneViewAction): IO[Unit] = action match {
    case SetNodes(nodes) => setNodes(nodes)
  }
}
