package de.tictactoe.util

trait Command {

  def doStep:Unit
  def undoStep:Unit
  def redoStep:Unit

}