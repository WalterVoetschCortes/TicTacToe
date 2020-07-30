package de.tictactoe.model.gameboardComponent.gameboardBaseImpl

import de.tictactoe.model.gameboardComponent.FieldInterface

/**
 * Field with isSet:Boolean, to indicate if field is set or not, and optional set piece (X or O)
 * to build the game board matrix
 * @param isSet
 * @param piece
 */
case class Field(isSet:Boolean, piece: Option[Piece.PieceVal] = None) extends FieldInterface {
  /**
   * shows the content of this field
   * @return
   */
  override def toString: String = piece.fold(" ")("".+)
}
