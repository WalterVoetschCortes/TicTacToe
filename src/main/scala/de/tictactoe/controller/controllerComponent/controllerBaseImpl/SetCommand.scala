package de.tictactoe.controller.controllerComponent.controllerBaseImpl

import de.tictactoe.util.Command


class SetCommand(player: Int, row:Int, col: Int, controller: Controller) extends Command {

  override def doStep: Unit =   controller.gameboard = controller.game.set(player, row, col)

  override def undoStep: Unit = controller.gameboard = controller.gameboard.removeXO(row, col)

  override def redoStep: Unit = controller.gameboard = controller.game.set(player, row, col)

}

