package de.tictactoe.controller

import de.tictactoe.controller.controllerComponent.controllerBaseImpl.{Controller, PlayState}
import de.tictactoe.model.gameboardComponent.gameboardBaseImpl.Gameboard
import org.scalatest.{Matchers, WordSpec}

class PlayStateSpec extends WordSpec with Matchers {
  "A SetStateSpec" when {
    val gameboard = new Gameboard(false)
    val controller = new Controller(gameboard)
    val state = new PlayState(controller)
    "created" should {
      "handle wrong input" in {
      state.handle("wrong input") should be("set your X or O with following input: s row col")
      }
      "handle when player set X or O" in {
        state.handle("s 0 0") should be("Set your X or O with following input: s row col\nPlayer1! it's your turn!")
      }
    }
  }
}
