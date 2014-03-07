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

object GUIMain extends JFXApp {
  val eventStrings = DefaultEventStrings
  val GUIInstances = new GUIInstances(eventStrings)
  def eventNodes(events: Seq[Event]) = events.map(GUIInstances.eventGUI.toNode)

  // TODO: Remove temporary stub.
  val defaultEvents = Seq(Grade("ספרות", "מבחן מחצית", 97), BehaviorEvent("אנגלית", Date(29, 1, 2014), "העדרות", "אוניברסיטה"))

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

  val nodes = for {
    grades <- grades
    behaviorEvents <- behaviorEvents
  } yield eventNodes(merge(grades, behaviorEvents))

  nodes.foreach(setNodes)

  def merge[A](seq1: Seq[A], seq2: Seq[A]): Seq[A] = (seq1 zip seq2).flatMap {
    case (a, b) => List(a, b)
  }


  override def stopApp() = requestRunner.mainRunner.system.shutdown()
  println()
}


