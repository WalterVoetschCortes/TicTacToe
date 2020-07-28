package de.tictactoe.model.gameboardComponent.gameboardBaseImpl

object Piece extends Enumeration {
  sealed case class PieceVal(value:String) {
    override def toString: String = value
  }

  val player1 = PieceVal("X")
  val player2 = PieceVal("O")
}
