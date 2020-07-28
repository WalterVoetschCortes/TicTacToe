package de.tictactoe.model.gameboardComponent.gameboardBaseImpl

import de.tictactoe.model.gameboardComponent.FieldInterface

case class Field(isSet:Boolean, piece: Option[Piece.PieceVal] = None) extends FieldInterface {
  override def toString: String = piece.fold(" ")("".+)
}
