package nightra.mashovNotificator.gui.model

import nightra.mashovNotificator.gui.model.Content._
import nightra.mashovNotificator.gui.model.Model.Model
import nightra.mashovNotificator.language.EventStrings
import nightra.mashovNotificator.model.{BehaviorEvent, Event, Grade}
import scalaz.syntax.show._
import scalaz._

import scalafx.scene.Node

object Model {
  type Model[Strings, A] = Reader[Strings, A => Node]
}

object EventModels {
  type EventModel[A] = Model[EventStrings, A]

  def gradeGUI: EventModel[Grade] = Reader {
    s => grade =>
      gradeEvent(List(
        field(s.subject, grade.subject),
        field(s.topic, grade.topic),
        field(s.grade, grade.grade.toString)
      ))
  }

  def behaviorGUI: EventModel[BehaviorEvent] = Reader {
    s => event =>
      behaviorEvent(List(
        field(s.subject, event.subject),
        field(s.date, event.date.shows),
        field(s.`type`, event.`type`),
        field(s.justification, event.justification)
      ))
  }

  def eventGUI: EventModel[Event] = Reader {
    s => {
      case grade: Grade => gradeGUI.run(s)(grade)
      case behavior: BehaviorEvent => behaviorGUI.run(s)(behavior)
    }
  }
}
