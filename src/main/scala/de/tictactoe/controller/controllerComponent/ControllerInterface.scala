package de.tictactoe.controller.controllerComponent

import de.tictactoe.model.gameboardComponent.gameboardBaseImpl.{Field, Matrix}
import de.tictactoe.model.playerComponent.Player

import scala.swing.Publisher

trait ControllerInterface extends Publisher{
  def handle(input:String):String
  def setPlayers(input:String):String
  def setMaxScore(input:String):String
  def createNewGame:String
  def createNewRound:String
  def set(row:Int, col:Int):String
  def gameboardToString:String
  def getField: Matrix[Field]
  def undo:String
  def redo:String
  def nextState:Unit
  def nextPlayer:Int
  def playerList:List[Player]
  def currentPlayerIndex:Int
  def maxScore:Int
  def player0Score:Int
  def player1Score:Int
  def load:String
  def save:String

}



import scala.swing.event.Event

class NewGame extends Event
class FieldChanged extends Event
class PlayerChanged extends Event
class GameFinishedWinner extends Event
class RoundFinishedDraw extends Event
class PlayerSwitch extends Event
class RoundFinishedWin extends Event
class ScoreChanged extends Event
class NewRound extends Event

