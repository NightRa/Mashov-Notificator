//Created By Ilan Godik
package nightra.mashovNotificator.model

import nightra.{mashovNotificator => m}
import m.network.requests.{BehaveEventsResponse, GradesResponse, BehaveEvent, GradeResponse}
import m.util.Date

trait DomainMorphism[A, B] {
  def convert: A => B
}

object DomainMorphism {
  def apply[A, B](implicit morph: DomainMorphism[A, B]) = morph.convert

  implicit def gradeDomain = new DomainMorphism[GradeResponse, Grade] {
    def convert = resp => Grade(resp.subjectName, resp.name, resp.grade, Date.parseDate(resp.visibleFrom))
  }

  implicit def gradesMorphism = new DomainMorphism[GradesResponse, Seq[Grade]] {
    def convert = _.grades.map(DomainMorphism[GradeResponse, Grade])
  }

  implicit def behaviorDomain = new DomainMorphism[BehaveEvent, BehaviorEvent] {
    def convert = resp =>
      BehaviorEvent(resp.subjectName, Date.parseDate(resp.lessonDate), resp.name, resp.justification)
  }

  implicit def behaviorsMorphism = new DomainMorphism[BehaveEventsResponse, Seq[BehaviorEvent]] {
    def convert = _.events.map(DomainMorphism[BehaveEvent, BehaviorEvent])
  }
}
