package nightra.mashovNotificator.gui.view

import nightra.mashovNotificator.gui.description.SceneDescription

import scalaz.effect.IO

trait View[D <: SceneDescription] {
  def applyViewAction(action: D#ViewAction): IO[Unit]
}
