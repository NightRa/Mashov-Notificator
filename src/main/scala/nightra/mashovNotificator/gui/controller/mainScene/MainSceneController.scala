package nightra.mashovNotificator.gui.controller.mainScene

import nightra.mashovNotificator.gui.controller.Controller
import nightra.mashovNotificator.gui.description.MainSceneDescription
import nightra.mashovNotificator.gui.model.EventModels
import nightra.mashovNotificator.gui.view.mainScene._
import nightra.mashovNotificator.language.EventStrings
import nightra.mashovNotificator.model.Event

object MainSceneController extends Controller[MainSceneDescription.type] {
  private def eventNodes(events: Seq[Event], eventStrings: EventStrings) =
    events.map(EventModels.eventGUI(eventStrings))


  def stateToViewAction(state: MainSceneState): MainSceneViewAction =
    state match {
      case Events(events, strings) => SetNodes(eventNodes(events, strings))
      // TODO: Loading Screen
      case Loading => SetNodes(Seq.empty)
    }


}
