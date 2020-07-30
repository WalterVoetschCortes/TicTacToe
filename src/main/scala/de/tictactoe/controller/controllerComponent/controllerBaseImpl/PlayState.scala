package de.tictactoe.controller.controllerComponent.controllerBaseImpl

case class PlayState(controller: Controller) extends ControllerState{

  override def handle(input: String): String = fixInput(input)

  def fixInput(input: String): String = {
    input.toList.filter(c => c != ' ') match {
      case 's' :: row :: col :: Nil => controller.set(row.toString.toInt, col.toString.toInt)
      case _ => "set your X or O with following input: s row col"
    }
  }

  override def nextState(): ControllerState = EnterPlayerState(controller)
}
