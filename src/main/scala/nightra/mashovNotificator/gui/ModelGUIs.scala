//Created By Ilan Godik
package nightra.mashovNotificator.gui

import scalafx.scene.Node
import scalaz.syntax.show._

import nightra.{mashovNotificator => m}
import m.model.{BehaviorEvent, Grade, Event}
import m.language.EventStrings
import Content.{field, gradeEvent, behaviorEvent}

object ModelGUIs {
  def gradeGUI(s: EventStrings): Grade => Node =
    model =>
      gradeEvent(List(
        field(s.subject, model.subject),
        field(s.topic, model.topic),
        field(s.grade, model.grade.toString)
      ))


  def behaviorGUI(s: EventStrings): BehaviorEvent => Node =
    model =>
      behaviorEvent(List(
        field(s.subject, model.subject),
        field(s.date, model.date.shows),
        field(s.`type`, model.`type`),
        field(s.justification, model.justification)
      ))

  def eventGUI(s: EventStrings): Event => Node = {
    case grade: Grade => gradeGUI(s)(grade)
    case behavior: BehaviorEvent => behaviorGUI(s)(behavior)
  }

  def eventsGUI(eventStrings: EventStrings)(events: Seq[Event]) =
    events.map(eventGUI(eventStrings))
}
