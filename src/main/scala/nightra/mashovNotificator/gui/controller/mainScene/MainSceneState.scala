package nightra.mashovNotificator.gui.controller.mainScene

import nightra.mashovNotificator.language.EventStrings
import nightra.mashovNotificator.model.Event

sealed trait MainSceneState
case object Loading extends MainSceneState
case class Events(events: Seq[Event], strings: EventStrings) extends MainSceneState