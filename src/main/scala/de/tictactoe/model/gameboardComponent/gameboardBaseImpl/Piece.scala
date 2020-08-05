package de.tictactoe.model.gameboardComponent.gameboardBaseImpl

/**
 * Piece for TicTacToe can be X (sets player0) or O (sets player1)
 */
object Piece extends Enumeration {
  sealed case class PieceVal(value:String) {
    override def toString: String = value
  }

  val player1 = PieceVal("X")
  val player2 = PieceVal("O")
  val empty = PieceVal(" ")
}
