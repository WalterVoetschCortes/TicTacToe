package de.tictactoe.aview

import de.tictactoe.controller.controllerComponent.{ControllerInterface, FieldChanged, GameFinishedWinner, NewGame, PlayerChanged, PlayerSwitch, RoundFinishedDraw, RoundFinishedWin, ScoreChanged}

import scala.swing.Reactor

class Tui(controller: ControllerInterface) extends Reactor {
  listenTo(controller)

  def processInputLine(input: String):String = {
    input match {
      case "q" =>"Bye bye! :)"
      case "n" => controller.createNewGame
      case "z" => controller.undo
      case "y" => controller.redo
      case "s" => controller.save
      case "l" => controller.load
      case _ => controller.handle(input)
    }
  }

  reactions +={
    case event: FieldChanged => printTui
    case event: ScoreChanged => println("MaxScore set to: " + controller.maxScore + "\n" +
      controller.playerList(controller.currentPlayerIndex) + "! it's your turn!\n" +
      "Set your X or O with following input: s row col")
      printTui
    case event: PlayerChanged => println("Player changed")
    case event: NewGame =>
      //printTui
      println("Created new game\nPlease enter the names like (player1 player2)")
    case event: RoundFinishedWin =>
      printTui
      println("New Round! Set your X or O with following input: s row col\n" +
        controller.playerList(controller.currentPlayerIndex).name  +
      "! It's your turn!\n" + controller.playerList(0).name + ": " + controller.player0Score + "\n" +
        controller.playerList(1).name + ": " + controller.player1Score )
    case event: GameFinishedWinner => printTui
      println("Game finished! " + controller.playerList(controller.currentPlayerIndex)
      + " has won the game!\n" +
        controller.playerList(controller.currentPlayerIndex).name + "! You won!!!\n"+
        controller.playerList(0).name + ": " + controller.player0Score + "\n" +
        controller.playerList(1).name + ": " + controller.player1Score +
      "\n\nPlease enter your names like (player1 player2) to start a new game!")
    case event: RoundFinishedDraw => printTui
      println("Round finished! No winner in this round! :( \n" +
        controller.playerList(0).name + ": " + controller.player0Score + "\n" +
        controller.playerList(1).name + ": " + controller.player1Score)
    case event: PlayerSwitch => println(controller.playerList(controller.currentPlayerIndex) + " it's your turn!")
  }

  def printTui: Unit = {
    println(controller.gameboardToString)
  }
}
