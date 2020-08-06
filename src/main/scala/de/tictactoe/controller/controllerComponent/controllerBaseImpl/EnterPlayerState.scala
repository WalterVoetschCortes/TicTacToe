package de.tictactoe.controller.controllerComponent.controllerBaseImpl

case class EnterPlayerState(controller: Controller) extends ControllerState{

  override def handle(input: String): String = controller.setPlayers(input)
  override def nextState(): ControllerState = EnterScoreState(controller)

}