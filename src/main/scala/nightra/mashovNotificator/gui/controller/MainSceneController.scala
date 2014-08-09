package nightra.mashovNotificator.gui.controller

import nightra.mashovNotificator.gui.model.EventModels
import nightra.mashovNotificator.gui.view._
import nightra.mashovNotificator.gui.view.mainScene._
import nightra.mashovNotificator.language.EventStrings
import nightra.mashovNotificator.model.Event

import scalaz.Reader


object MainSceneController {

  private def eventNodes(events: Seq[Event], eventStrings: EventStrings) =
    events.map(EventModels.eventGUI(eventStrings))



  def stateToViewAction(state: MainSceneState): ViewAction =
    state match {
      case Events(events, strings) => SetNodes(eventNodes(events, strings))
      // TODO: Loading Screen
      case Loading => SetNodes(Seq.empty)
    }


}
