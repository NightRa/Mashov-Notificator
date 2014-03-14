//Created By Ilan Godik
package nightra.mashovNotificator.network.requests

import org.scalatest.{Matchers, FlatSpec}
import argonaut.Argonaut._
import scalaz.\/.right

class ParseRequestsTest extends FlatSpec with Matchers {
  "Parsing BehaveEvent" should "parse it correctly" in {
    val input = """{
                  |        "studentId": 376308302,
                  |        "lessonId": 27473,
                  |        "eventCode": 2,
                  |        "justified": -1,
                  |        "reporterid": 17682006,
                  |        "groupid": 11193003,
                  |        "lesson": 3,
                  |        "lessondate": "2014-01-09T00:00:00",
                  |        "name": "העדרות",
                  |        "justification": "ללא הערות",
                  |        "teacherName": "שורק פיטר",
                  |        "subjectName": "אנגלית MODULE F",
                  |        "remarktext": ""
                  |    }""".stripMargin
    val expected = BehaveEvent(376308302, 27473, 2, -1, 17682006, 11193003, 3, "2014-01-09T00:00:00", "העדרות", "ללא הערות", "שורק פיטר", "אנגלית MODULE F", "")
    input.decode[BehaveEvent] should equal(right(expected))
  }

  "Parsing BehaveEvents" should "parse them correctly" in {
    val input = """{"tblStudentAchva": [
                  |    {
                  |        "studentId": 376308302,
                  |        "lessonId": 27473,
                  |        "eventCode": 2,
                  |        "justified": -1,
                  |        "reporterid": 17682006,
                  |        "groupid": 11193003,
                  |        "lesson": 3,
                  |        "lessondate": "2014-01-09T00:00:00",
                  |        "name": "העדרות",
                  |        "justification": "ללא הערות",
                  |        "teacherName": "שורק פיטר",
                  |        "subjectName": "אנגלית MODULE F",
                  |        "remarktext": ""
                  |    },
                  |    {
                  |        "studentId": 376308302,
                  |        "lessonId": 26440,
                  |        "eventCode": 2,
                  |        "justified": -1,
                  |        "reporterid": 24621054,
                  |        "groupid": 11011008,
                  |        "lesson": 3,
                  |        "lessondate": "2014-01-06T00:00:00",
                  |        "name": "העדרות",
                  |        "justification": "ללא הערות",
                  |        "teacherName": "פרידמן דורית",
                  |        "subjectName": "ספרות",
                  |        "remarktext": ""
                  |    }]}""".stripMargin
    val event1 = BehaveEvent(376308302, 27473, 2, -1, 17682006, 11193003, 3, "2014-01-09T00:00:00", "העדרות", "ללא הערות", "שורק פיטר", "אנגלית MODULE F", "")
    val event2 = BehaveEvent(376308302, 26440, 2, -1, 24621054, 11011008, 3, "2014-01-06T00:00:00", "העדרות", "ללא הערות", "פרידמן דורית", "ספרות", "")
    val expected = BehaveEventsResponse(Vector(event1, event2))
    input.decode[BehaveEventsResponse] should be(right(expected))
  }

  "Parsing GradeResponse" should "parse it correctly" in {
    val input = """{
                  |        "subjectName": "עברית ",
                  |        "Name": "בוחן-עד משפט פשוט בעל חלק כולל",
                  |        "grade": 92,
                  |        "GradeComment": null,
                  |        "rate": 0.0,
                  |        "teacherName": "שרף מירב",
                  |        "eDate": "2014-01-08T00:00:00",
                  |        "visiblefrom": "2014-01-08T00:00:00"
                  |    }""".stripMargin
    val expected = GradeResponse("עברית ", "בוחן-עד משפט פשוט בעל חלק כולל", 92, None, 0.0, "שרף מירב", "2014-01-08T00:00:00", "2014-01-08T00:00:00")
    input.decode[GradeResponse] should be(right(expected))
  }

  "Parsing Grades Response" should "Parse them correctly" in {
    val input = """{"allGrades": [
                  |    {
                  |        "subjectName": "עברית ",
                  |        "Name": "בוחן-עד משפט פשוט בעל חלק כולל",
                  |        "grade": 92,
                  |        "GradeComment": null,
                  |        "rate": 0.0,
                  |        "teacherName": "שרף מירב",
                  |        "eDate": "2014-01-08T00:00:00",
                  |        "visiblefrom": "2014-01-08T00:00:00"
                  |    },
                  |    {
                  |        "subjectName": "תנ\"ך ",
                  |        "Name": "סיפורי האבות ומלכים ב",
                  |        "grade": 98,
                  |        "GradeComment": null,
                  |        "rate": 40.0,
                  |        "teacherName": "ברקאי עפרה",
                  |        "eDate": "2014-01-05T00:00:00",
                  |        "visiblefrom": "2014-01-05T00:00:00"
                  |    }]}""".stripMargin
    val grade1 = GradeResponse("עברית ", "בוחן-עד משפט פשוט בעל חלק כולל", 92, None, 0.0, "שרף מירב", "2014-01-08T00:00:00", "2014-01-08T00:00:00")
    val grade2 = GradeResponse("תנ\"ך ", "סיפורי האבות ומלכים ב", 98, None, 40.0, "ברקאי עפרה", "2014-01-05T00:00:00", "2014-01-05T00:00:00")
    val expected = GradesResponse(Vector(grade1, grade2))
    input.decode[GradesResponse] should be(right(expected))
  }

  "Parsing Ticks Response" should "Parse it correctly" in {
    val input =
      """{"ticks":[{"ticks":"635304156250170000"}]}"""
    val expected = TickResponse(635304156250170000L)
    input.decode[TickResponse] should be(right(expected))
  }

  "Parsing Login Response" should "parse correctly" in {
    val input =
      """{"loginInfo":[{"session":"3750","usertype":"1"}]}"""
    val expected = LoginResponse(3750, 1)
    input.decode[LoginResponse] should be(right(expected))
  }
}
