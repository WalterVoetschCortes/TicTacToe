package de.tictactoe

import de.tictactoe.model.gameboardComponent.gameboardBaseImpl.{Gameboard, Piece}

import scala.io.StdIn.readLine

object TicTacToe {

  def main(args: Array[String]): Unit = {
    println("Welcome to TicTacToe")
    var gameboard = new Gameboard(false)
    println(gameboard)
    gameboard = gameboard.addXO(0,0,Piece.PieceVal("X"))
    println(gameboard)
    gameboard = gameboard.addXO(1,0,Piece.PieceVal("O"))
    println(gameboard)
  }

}
