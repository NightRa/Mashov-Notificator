//Created By Ilan Godik
package nightra.mashovNotificator.gui

import scalafx.scene.Node
import scalaz.syntax.show._

import nightra.{mashovNotificator => m}
import m.model.{BehaviorEvent, Grade, Event}
import m.language.EventStrings
import Content.{field, gradeEvent, behaviorEvent}

class GUIInstances(s: EventStrings) {
  def gradeGUI = new GUIRepresentation[Grade] {
    def toNode =
      model =>
        gradeEvent(List(
          field(s.subject, model.subject),
          field(s.topic, model.topic),
          field(s.grade, model.grade.toString)
        ))
  }

  def behaviorGUI = new GUIRepresentation[BehaviorEvent] {
    def toNode =
      model =>
        behaviorEvent(List(
          field(s.subject, model.subject),
          field(s.date, model.date.shows),
          field(s.`type`, model.`type`),
          field(s.justification, model.justification)
        ))
  }

  implicit def eventGUI = new GUIRepresentation[Event] {
    def toNode = {
      case grade: Grade => gradeGUI.toNode(grade)
      case behavior: BehaviorEvent => behaviorGUI.toNode(behavior)
    }
  }
}

trait GUIRepresentation[A] {
  def toNode: A => Node
}
