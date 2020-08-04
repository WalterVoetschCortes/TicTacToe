package de.tictactoe.model.gameboardAdvancedImpl

import de.tictactoe.model.gameboardComponent.gameboardAdvancedImpl.Gameboard
import org.scalatest.{Matchers, WordSpec}

class GameboardSpec extends WordSpec with Matchers{
  "A Gameboard" should {
    val gameboard = new Gameboard(false)
    "have beend created new" in {
      gameboard.createNewGameboard.toString should be("   0     1     2  \n+-----+-----+-----+\n|     |     |     | 0\n+-----+-----+-----+\n|     |     |     | 1\n+-----+-----+-----+\n|     |     |     | 2\n+-----+-----+-----+\n**********  TicTacToe  **********\n\nn:   create a new empty game board\nz:   undo\ny:   redo\ns:   save\nl:   load\nq:   quit the programm\n")
    }
  }
}