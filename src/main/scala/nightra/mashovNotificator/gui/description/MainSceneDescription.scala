package nightra.mashovNotificator.gui.description

import nightra.mashovNotificator.gui.controller.mainScene.MainSceneState
import nightra.mashovNotificator.gui.view.mainScene.{MainSceneViewAction, MainScene}

object MainSceneDescription extends SceneDescription {
  type State = MainSceneState
  type ViewAction = MainSceneViewAction
}
