package de.tictactoe.controller.controllerComponent.controllerBaseImpl

abstract class ControllerState {

  def handle(input: String): String
  def nextState(): ControllerState

}
