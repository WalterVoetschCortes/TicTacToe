package de.tictactoe.controller.controllerComponent.controllerBaseImpl

case class EnterScoreState(controller: Controller) extends ControllerState{

  override def handle(input: String): String = controller.setMaxScore(input)
  override def nextState(): ControllerState = PlayState(controller)

}