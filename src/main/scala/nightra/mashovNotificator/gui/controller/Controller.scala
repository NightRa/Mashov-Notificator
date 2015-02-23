package nightra.mashovNotificator.gui.controller

import nightra.mashovNotificator.gui.description.SceneDescription
import nightra.mashovNotificator.gui.view.View

import scalaz.effect.IO

trait Controller[D <: SceneDescription] {

  def stateToViewAction(state: D#State): D#ViewAction

  // ----------------------------------------DERIVED--------------------------------------------------------------------

  def updateSceneState(view: View[D], state: D#State): IO[Unit] =
    view.applyViewAction(stateToViewAction(state))
}
