package de.tictactoe.controller.controllerComponent

import de.tictactoe.model.gameboardComponent.gameboardBaseImpl.{Field, Matrix}
import de.tictactoe.model.playerComponent.Player

import scala.swing.Publisher

trait ControllerInterface extends Publisher{
  def handle(input:String):String
  def setPlayers(input:String):String
  def createEmptyGameboard:String
  def set(row:Int, col:Int):String
  def gameboardToString:String
  def undo:String
  def redo:String
  def nextState:Unit
  def nextPlayer:Int
  def playerList:List[Player]
  def currentPlayerIndex:Int
  def load:String
  def save:String
}



import scala.swing.event.Event

class NewGame extends Event
class FieldChanged extends Event
class PlayerChanged extends Event
class GameFinishedWinner extends Event
class GameFinishedDraw extends Event
class PlayerSwitch extends Event

