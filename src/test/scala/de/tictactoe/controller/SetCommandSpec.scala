package de.tictactoe.controller

import de.tictactoe.controller.controllerComponent.controllerBaseImpl.{Controller, SetCommand}
import de.tictactoe.model.gameboardComponent.gameboardBaseImpl.Gameboard
import org.scalatest.{Matchers, WordSpec}

class SetCommandSpec extends WordSpec with Matchers {
  "A SetCommandSpec" when {
    val gameboard = new Gameboard(false)
    val controller = new Controller(gameboard)
    val command1 = new SetCommand(0, 0,0, controller)
    val command2 = new SetCommand(1, 1,0, controller)


    "created" should {
      "undoStep with player0" in {
        command1.undoStep should be()
      }
      "redoStep with player0" in {
        command1.redoStep should be()
      }
      "undoStep with playe1" in {
        command2.undoStep should be()
      }
      "redoStep with player1" in {
        command2.redoStep should be()
      }
    }
  }
}
