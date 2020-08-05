package de.tictactoe.aview

import de.tictactoe.controller.controllerComponent.{ControllerInterface, FieldChanged, GameFinishedDraw, GameFinishedWinner, NewGame, PlayerChanged, PlayerSwitch}

import scala.swing.Reactor

class Tui(controller: ControllerInterface) extends Reactor {
  listenTo(controller)

  def processInputLine(input: String):String = {
    input match {
      case "q" =>"Bye bye! :)"
      case "n" => controller.createEmptyGameboard
      case "z" => controller.undo
      case "y" => controller.redo
      case "s" => controller.save
      case "l" => controller.load
      case _ => controller.handle(input)
    }
  }

  reactions +={
    case event: FieldChanged => printTui
    case event: PlayerChanged => println(controller.playerList(controller.currentPlayerIndex) + "! it's your turn!\n" +
      "Set your X or O with following input: s row col")
    case event: NewGame =>
      printTui
      println("Created new game board\nPlease enter the names like (player1 player2)")
    case event: GameFinishedWinner => printTui
      println("Game finished! " + controller.playerList(controller.currentPlayerIndex)
      + " has won the game!\n" +
        controller.playerList(controller.currentPlayerIndex).name + "! You won!!!\n")
    case event: GameFinishedDraw => printTui
      println("Game finished! No winner! :(")
    case event: PlayerSwitch => println(controller.playerList(controller.currentPlayerIndex) + " it's youre turn!")
  }

  def printTui: Unit = {
    println(controller.gameboardToString)
  }
}
