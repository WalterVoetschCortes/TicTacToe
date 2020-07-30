package de.tictactoe.model.gameboardComponent.gameboardBaseImpl

import com.google.inject.{Guice, Inject}
import de.tictactoe.model.gameboardComponent.GameboardInterface

/**
 * Game board case class builds a matrix of fields of dimension 3x3 for TicTacToe.
 * @param fields
 */
case class Gameboard @Inject() (fields: Matrix[Field]) extends GameboardInterface{

  def this (isSet: Boolean) = this(new Matrix[Field](Field(isSet)))

  /**
   * can set X or O in (row,col) field of game board matrix
   * @param row
   * @param col
   * @param playerValue
   * @return
   */
  override def addXO(row: Int, col: Int, playerValue: Piece.PieceVal): Gameboard = copy(fields.updateField(row, col, Field(true, Some(playerValue))))

  /**
   * removes X or O in field (row,col) for UNDO-Step
   * @param row
   * @param col
   * @return
   */
  override def removeXO(row: Int, col: Int): Gameboard = copy(fields.updateField(row, col, Field(false,None)))

  /**
   * Shows the legend for text-based user interface (tui)
   * @return
   */
  override def legend: String = {
    val welcome = "**********  TicTacToe  **********\n\n"
    val n = "n:   create a new empty game board\n"
    val z = "z:   undo\n"
    val y = "y:   redo\n"
    val s = "s:   save\n"
    val l = "l:   load\n"
    val q = "q:   quit the programm\n"
    welcome + n + z + y + s + l + q
  }

  /**
   * to build the frame of game board matrix in toString method for TUI
   * @return
   */
  override def frame(row: Int): String = {
    val plus = "+"
    val line = "-"
    val combine = (plus + line * 5) * row + plus
    combine
  }

  /**
   * creates new game board matrix
   * @return
   */
  override def createNewGameboard: GameboardInterface = new Gameboard(false)

  /**
   * toString method for TUI, shows complete game board matrix with set Xs and Os
   * @return
   */
  override def toString:String = {
    val pipe = "|"
    val new_line = "\n"
    var matchField = ""

    for(rowNumbers <- 0 until 3) matchField += "   " + rowNumbers + "  "
    matchField += new_line + frame(3) + new_line
    for { row <- 0 until 3
          col <- 0 until 3 }
    {
      if (fields.field(row, col).isSet) {
        if (fields.field(row,col).piece.get.value.equals("X")) {
          matchField += "|  " + "\033[0;34m" + fields.field(row,col) + Console.RESET + "  "
        } else {
          matchField += "|  " + "\033[0;31m" + fields.field(row,col) + Console.RESET + "  "
        }
      } else {
        matchField += "|     "
      }
      if (col == 2) {
        matchField += pipe + " " + row + new_line
        matchField += frame(3) + new_line
      }
    }
    matchField += legend
    matchField
  }
}
