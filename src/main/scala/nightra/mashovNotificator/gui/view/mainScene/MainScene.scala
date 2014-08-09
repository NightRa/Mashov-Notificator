package nightra.mashovNotificator.gui.view.mainScene

import nightra.mashovNotificator.gui.view.{SetNodes, ViewAction, View}
import nightra.mashovNotificator.model.{BehaviorEvent, Grade}
import nightra.mashovNotificator.util.Date

import scalafx.application.Platform
import scalafx.beans.property.ObjectProperty
import scalafx.scene.{Node, Scene}
import scalaz.effect.IO

class MainScene(initial: ViewAction) extends Scene(800, 600) with View {
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

  def applyViewAction(action: ViewAction): IO[Unit] = action match {
    case SetNodes(nodes) => setNodes(nodes)
  }
}