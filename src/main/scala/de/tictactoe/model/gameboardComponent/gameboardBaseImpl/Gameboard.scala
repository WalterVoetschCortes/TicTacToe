package de.tictactoe.model.gameboardComponent.gameboardBaseImpl

import de.tictactoe.model.gameboardComponent.GameboardInterface

case class Gameboard(fields: Matrix[Field]) extends GameboardInterface{

  def this (isSet: Boolean) = this(new Matrix[Field](Field(isSet)))

  override def addXO(row: Int, col: Int, playerValue: Piece.PieceVal): Gameboard = copy(fields.updateField(row, col, Field(true, Some(playerValue))))

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

  override def frame(row: Int): String = {
    val plus = "+"
    val line = "-"
    val combine = (plus + line * 5) * row + plus
    combine
  }

  override def createNewGameboard: GameboardInterface = new Gameboard(false)

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
