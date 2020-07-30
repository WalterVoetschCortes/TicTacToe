package de.tictactoe.model.gameboardComponent

import de.tictactoe.model.gameboardComponent.gameboardBaseImpl.{Field, Matrix, Piece}

trait GameboardInterface {
  def fields: Matrix[Field]
  def addXO(row:Int,col:Int, playerValue: Piece.PieceVal): GameboardInterface
  def removeXO(row: Int, col: Int): GameboardInterface
  def legend:String
  def frame(row:Int):String
  def createNewGameboard:GameboardInterface
}

trait FieldInterface{
  def isSet:Boolean
  def piece: Option[Piece.PieceVal]

}