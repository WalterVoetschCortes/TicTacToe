package de.tictactoe.model.playerComponent

/**
 * Player for TicTacToe has just a name
 * @param name
 */
case class Player(name: String) {

  /**
   * returns name of that player
   * @return
   */
  override def toString: String = name
}

