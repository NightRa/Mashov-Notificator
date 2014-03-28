package nightra.mashovNotificator.model

import nightra.mashovNotificator.model.Grade._
import nightra.mashovNotificator.model.BehaviorEvent._
import nightra.mashovNotificator.util.Date
import nightra.mashovNotificator.util.Date._
import org.scalatest.{Matchers, FlatSpec}
import argonaut._, Argonaut._

class EventModelJsonTest extends FlatSpec with Matchers {
  val date = Date(12, 3, 2014)
  val grade = Grade("English", "Mid-Exam", 100, date)
  val behaviorEvent = BehaviorEvent("English", "miss", "None", date)


  "Encode grade event" should "produce the appripriate Json." in {
    val expected = Json("subject" := "English", "topic" := "Mid-Exam", "grade" := 100, "date" := date)
    grade.asJson should be(expected)
  }

  it should "be invertible by Decode." in {
    grade.asJson.jdecode[Grade] should equal(DecodeResult.ok(grade))
  }

  "Encode behavior event" should "produce the appropriate Json." in {
    val expected = Json("subject" := "English", "type" := "miss", "justification" := "None", "date" := date)
    behaviorEvent.asJson should be(expected)
  }

  it should "be invertible by Decode." in {
    behaviorEvent.asJson.jdecode[BehaviorEvent] should equal(DecodeResult.ok(behaviorEvent))
  }
}
