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
    "handle set X of player1 in field(0,0)" in{
      tui.processInputLine("s 0 0")
    }
    "handle set O of player2 in field(0,1)" in{
      tui.processInputLine("s 0 1")
    }
    "handle set X of player1 in field(1,1)" in{
      tui.processInputLine("s 1 1")
    }
    "handle set O of player2 in field(0,2)" in{
      tui.processInputLine("s 0 2")
    }
    "handle set X of player1 in field(2,2) and win" in{
      tui.processInputLine("s 2 2")
    }
    "create again empty Game Board with input 'n'" in {
      tui.processInputLine("n")
      controller.gameboard should be(new Gameboard(false))
    }
    "handle set players input p1 and p2" in{
      tui.processInputLine("p1 p2")
    }
    "handle set X of p1 in field(0,0)" in{
      tui.processInputLine("s 0 0")
    }
    "handle set O of p2 in field(1,1)" in{
      tui.processInputLine("s 1 1")
    }
    "handle set X of p1 in field(1,0)" in{
      tui.processInputLine("s 1 0")
    }
    "handle set O of p2 in field(2,0)" in{
      tui.processInputLine("s 2 0")
    }
    "handle set X of p1 in field(0,2)" in{
      tui.processInputLine("s 0 2")
    }
    "handle set O of p2 in field(0,1)" in{
      tui.processInputLine("s 0 1")
    }
    "handle set X of p1 in field(2,1)" in{
      tui.processInputLine("s 2 1")
    }
    "handle set O of p2 in field(1,2) and the game ended in a tie" in{
      tui.processInputLine("s 1 2")
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