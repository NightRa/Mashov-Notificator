//Created By Ilan Godik
package nightra.mashovNotificator.gui

import scalafx.application.{Platform, JFXApp}
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.{Node, Scene}
import scalafx.beans.property.ObjectProperty

import nightra.{mashovNotificator => m}
import m.model.{DomainMorphism, Event, BehaviorEvent, Grade}
import m.util.Date
import m.language.DefaultEventStrings
import m.main.TestRequestRun
import m.network.requests.{BehaveEvent, GradeResponse, BehaveEventsResponse, GradesResponse}
import java.io.PrintWriter

object GUIMain extends JFXApp {
  val eventStrings = DefaultEventStrings
  val GUIInstances = new GUIInstances(eventStrings)
  def eventNodes(events: Seq[Event]) = events.map(GUIInstances.eventGUI.toNode)

  // TODO: Remove temporary stub.
  val defaultEvents = Seq(Grade("ספרות", "מבחן מחצית", 97, Date(29, 1, 2014)), BehaviorEvent("אנגלית", Date(29, 1, 2014), "העדרות", "אוניברסיטה"))

  val eventNodesProperty: ObjectProperty[Seq[Node]] = ObjectProperty(eventNodes(defaultEvents))

  stage = new PrimaryStage {
    scene = new Scene(800, 600) {
      sceneSelf =>
      content = new GUI(eventNodesProperty) {
        prefHeight <== sceneSelf.height
        prefWidth <== sceneSelf.width
      }
    }
  }

  def setNodes(nodes: Seq[Node]) = Platform.runLater(eventNodesProperty.set(nodes))

  val requestRunner = new TestRequestRun

  import requestRunner.mainRunner._

  val keyFuture = requestRunner.requestKey(requestRunner.credentials)
  val gradesFuture = keyFuture.flatMap(requestRunner.requestGrades)
  val behaviorFuture = keyFuture.flatMap(requestRunner.requestBehaviorEvents)
  val grades = gradesFuture.map(DomainMorphism[GradesResponse, Seq[Grade]])
  val behaviorEvents = behaviorFuture.map(DomainMorphism[BehaveEventsResponse, Seq[BehaviorEvent]])

  val events = for {
    grades <- grades
    behaviorEvents <- behaviorEvents
  } yield combineEvents(grades, behaviorEvents)

  val nodes = events.map(eventNodes)

  nodes.foreach(setNodes)

  def combineEvents(events: Seq[Event]*): Seq[Event] = events.flatten.sortBy(_.date)(Date.dateOrder.reverseOrder.toScalaOrdering)

  // TODO: Remove pickling test.
  /*grades.foreach{
    completeGrades =>
    val outputStream = new PrintWriter("grades-json.save")
    val encoded = completeGrades.map(grade => grade.copy(subject = grade.subject.trim)).pickle.value
    outputStream.write(encoded)
    outputStream.close()
  }*/


  override def stopApp() = requestRunner.mainRunner.system.shutdown()
  println()
}


