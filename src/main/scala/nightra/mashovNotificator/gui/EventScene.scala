package nightra.mashovNotificator.gui

import scalafx.scene.{Node, Scene}
import nightra.mashovNotificator.model.{Event, BehaviorEvent, Grade}
import nightra.mashovNotificator.util.Date
import scalafx.beans.property.ObjectProperty
import nightra.mashovNotificator.language.EventStrings
import scalaz.effect.IO
import scalafx.application.Platform

class EventScene(defaultNodes: Seq[Node]) extends Scene(800, 600) {
  self =>
  val eventNodesProperty: ObjectProperty[Seq[Node]] = ObjectProperty(defaultNodes)

  content = new GUI(eventNodesProperty) {
    prefHeight <== self.height
    prefWidth <== self.width
  }

  def setNodes(nodes: Seq[Node]): IO[Unit] = IO {
    Platform.runLater(eventNodesProperty.set(nodes))
  }
}
