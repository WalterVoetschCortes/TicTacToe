package de.tictactoe.model.gameboardComponent.gameboardBaseImpl

import de.tictactoe.model.gameboardComponent.GameboardInterface
import de.tictactoe.model.playerComponent.Player

/**
 * Game for TicTacToe has two players and a game board.
 * Considers gameplay logic once a piece has been set.
 * @param playerA
 * @param playerB
 * @param gameboard
 */
case class Game(var playerA: Player, var playerB: Player, var gameboard: GameboardInterface){

  /**
   * sets players involved in the Game with input:String (input like: Torres Iniesta)
   * returns a list of these players
   * @param input
   * @return
   */
  def setPlayers(input: String): List[Player] = {
    input.split(" ").map(_.trim).toList match{
      case player1 :: player2 :: Nil =>
        playerA = playerA.copy(player1)
        playerB = playerA.copy(player2)
        List[Player](playerA,playerB)
    }
  }

  /*
   * player0 sets x with setX method and player1 sets O with setO method
   * @param player
   * @param row
   * @param col
   * @return
   */
  def set(player: Int, row:Int, col:Int): GameboardInterface ={
    if( 0 > row || 2 < row || 0 > col || 2 < col ){ //out of bounds
      return gameboard
    } else if (gameboard.fields.field(row,col).isSet){//field is already set
      return gameboard
    }
    player match{
      case 0 => return setX(player, row, col)
      case 1 => return setO(player, row, col)
    }
    gameboard
  }

  private def setX(player:Int, row:Int, col:Int): GameboardInterface ={
    gameboard = gameboard.addXO(row,col,Piece.PieceVal("X"))
    gameboard
  }

  private def setO(player:Int, row:Int, col:Int): GameboardInterface ={
    gameboard = gameboard.addXO(row,col,Piece.PieceVal("O"))
    gameboard
  }

}
