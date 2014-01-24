//Created By Ilan Godik
package nightra.mashovNotificator.network.readers

import nightra.mashovNotificator.network.requests.{GradeResponse, GradesResponse}
import spray.json._
import spray.json.DefaultJsonProtocol._
import spray.json.lenses.JsonLenses._

object GradesReader extends RootJsonReader[GradesResponse]{
  def read(json: JsValue):GradesResponse =
    GradesResponse(json.extract[GradeResponse]('allGrades / elements))
}
