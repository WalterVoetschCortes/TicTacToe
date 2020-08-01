package de.tictactoe.aview

import de.tictactoe.controller.controllerComponent.controllerBaseImpl.Controller
import de.tictactoe.model.gameboardComponent.gameboardBaseImpl.Gameboard
import org.scalatest.{Matchers, WordSpec}

class TuiSpec extends WordSpec with Matchers{

  "A Stratego Tui" should {
    val controller = new Controller(new Gameboard(false))
    val tui = new Tui(controller)

    "create empty Game Board with input 'n'" in {
      tui.processInputLine("n")
      controller.gameboard should be(new Gameboard(false))
    }
    "quit" in {
      tui.processInputLine("q")
    }
    "undo" in {
      tui.processInputLine("z")
    }
    "redo" in {
      tui.processInputLine("y")
    }
    "handle wrong input" in{
      tui.processInputLine("asdf")
    }
    "handle set players input" in{
      tui.processInputLine("player1 player2")
    }

    /*
    "save" in {
      tui.processInputLine("s")
    }
    "load" in {
      tui.processInputLine("l")
    }
     */
  }
}