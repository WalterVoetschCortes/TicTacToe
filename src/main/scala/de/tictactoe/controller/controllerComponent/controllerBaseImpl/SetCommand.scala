package de.tictactoe.controller.controllerComponent.controllerBaseImpl

import de.tictactoe.model.gameboardComponent.gameboardBaseImpl.Piece
import de.tictactoe.util.Command


class SetCommand(player: Int, row:Int, col: Int, controller: Controller) extends Command {

  override def doStep: Unit = controller.gameboard = {
    if(player == 0){
      controller.gameboard.addXO(row,col,Piece.PieceVal("X"))
    } else{
      controller.gameboard.addXO(row,col,Piece.PieceVal("O"))
    }
  }

  override def undoStep: Unit = controller.gameboard = controller.gameboard.removeXO(row, col)

  override def redoStep: Unit = controller.gameboard = {
    if(player == 0){
      controller.gameboard.addXO(row,col,Piece.PieceVal("X"))
    } else{
      controller.gameboard.addXO(row,col,Piece.PieceVal("O"))
    }
  }

}

