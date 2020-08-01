package de.tictactoe.model

import de.tictactoe.model.gameboardComponent.gameboardBaseImpl.{Gameboard, Piece}
import org.scalatest.{Matchers, WordSpec}

class GameboardSpec extends WordSpec with Matchers {

  "A Gameboard" when {
    "created" should {
      var gameboard = new Gameboard(false)

      "can set X in field (0,0)" in {
        gameboard = gameboard.addXO(0,0, Piece.PieceVal("X"))
      }
      "can set O in field (0,1)" in {
        gameboard = gameboard.addXO(0,1, Piece.PieceVal("O"))
      }
      "can print the game board" in {
        gameboard.toString should be("   0     1     2  \n+-----+-----+-----+\n|  \u001B[0;34mX\u001B[0m  |  \u001B[0;31mO\u001B[0m  |     | 0\n+-----+-----+-----+\n|     |     |     | 1\n+-----+-----+-----+\n|     |     |     | 2\n+-----+-----+-----+\n**********  TicTacToe  **********\n\nn:   create a new empty game board\nz:   undo\ny:   redo\ns:   save\nl:   load\nq:   quit the programm\n")
      }
      "can create a new Gameboard" in {
        gameboard.createNewGameboard
      }
    }
  }
}
