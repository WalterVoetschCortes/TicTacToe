package de.tictactoe.controller.controllerComponent.controllerBaseImpl

import com.google.inject.{Guice, Inject}
import de.tictactoe.TicTacToeModule
import de.tictactoe.controller.controllerComponent.{ControllerInterface, FieldChanged, GameFinishedWinner, NewGame, NewRound, PlayerChanged, PlayerSwitch, RoundFinishedDraw, ScoreChanged}
import de.tictactoe.model.gameboardComponent.GameboardInterface
import de.tictactoe.model.gameboardComponent.gameboardBaseImpl.Gameboard
import de.tictactoe.model.playerComponent.Player
import de.tictactoe.util.UndoManager

import scala.util.control.Breaks._
import scala.swing.Publisher

class Controller @Inject()(var gameboard:GameboardInterface) extends ControllerInterface with Publisher {

  val injector = Guice.createInjector(new TicTacToeModule)
  //val fileIO = injector.getInstance(classOf[FileIOInterface])

  var player0 = Player("Player0")
  var player1 = Player("Player1")
  var playerList = List[Player](player0,player1)
  var currentPlayerIndex: Int = 0
  var setCount = 0
  var player0Score = 0
  var player1Score = 0
  var maxScore = 3

  private val undoManager = new UndoManager
  var state: ControllerState = EnterPlayerState(this)


  override def handle(input: String): String = {
    state.handle(input)
  }

  /**
   * sets players involved in the Game with input:String
   * puts player names in the list playerList
   * @param input
   * @return
   */
  override def setPlayers(input: String): String = {
    input.split(" ").map(_.trim).toList match{
      case playerA :: playerB :: Nil =>
        player0 = player0.copy(playerA)
        player1 = player1.copy(playerB)
        playerList = List[Player](player0,player1)
      case _ => return "Wrong input!! Try it again!"
    }
    state = state.nextState
    publish(new PlayerChanged)
    "Now set the maximum score between 0 and 100"
  }

  override def setMaxScore(input: String): String ={
    try {
      input.toInt
    } catch {
      case e: Exception => return "Wrong input! You have to set the maximum score between 0 and 100! Try it again!"
    }

    if(100 > input.toInt && input.toInt> 0){
      maxScore = input.toInt
    } else {
      return "Maximum score has to be between 0 and 100!! xxxTry it again!"
    }

    publish(new ScoreChanged)
    state = state.nextState
    ""
  }

  override def createNewGame:String ={
    setCount = 0
    currentPlayerIndex = 0
    maxScore = 3
    player0Score = 0
    player1Score = 0
    gameboard= new Gameboard(false)
    state = EnterPlayerState(this)
    currentPlayerIndex=0
    publish(new NewGame)
    ""
  }

  override def createNewRound: String = {
    setCount = 0
    gameboard= new Gameboard(false)
    currentPlayerIndex=nextPlayer
    publish(new PlayerSwitch)
    publish(new NewRound)

    ""
  }

  private def checkWin(row:Int, col:Int): Boolean = {
    //check row
    breakable {
      for(i <- 0 to 2) {
        if (currentPlayerIndex == 0) {
          if(!gameboard.fields.field(row,i).isSet) break
          if (gameboard.fields.field(row, i).isSet) {
            if (!gameboard.fields.field(row, i).piece.get.value.equals("X")) break
          }
        } else {
          if(!gameboard.fields.field(row,i).isSet) break
          if (gameboard.fields.field(row, i).isSet) {
            if (!gameboard.fields.field(row, i).piece.get.value.equals("O")) break
          }
        }
        if (i == 2) {
          return true
        }
      }
    }

    //check col
    breakable {
      for(i <- 0 to 2) {
        if (currentPlayerIndex == 0) {
          if(!gameboard.fields.field(i,col).isSet) break
          if (gameboard.fields.field(i, col).isSet) {
            if (!gameboard.fields.field(i, col).piece.get.value.equals("X")) break
          }
        } else {
          if(!gameboard.fields.field(i,col).isSet) break
          if (gameboard.fields.field(i, col).isSet) {
            if (!gameboard.fields.field(i, col).piece.get.value.equals("O")) break
          }
        }
        if (i == 2) {
          return true
        }
      }
    }


    //check diag
    breakable {
      if(row == col){
        for(i <- 0 to 2){
          if (currentPlayerIndex == 0) {
            if(!gameboard.fields.field(i,i).isSet) break()
            if (gameboard.fields.field(i, i).isSet) {
              if (!gameboard.fields.field(i, i).piece.get.value.equals("X")) break
            }
          } else {
            if(!gameboard.fields.field(i,i).isSet) break()
            if (gameboard.fields.field(i, i).isSet) {
              if (!gameboard.fields.field(i, i).piece.get.value.equals("O")) break
            }
          }
          if(i == 2){
            return true
          }
        }
      }
    }

    //check anti diag
    breakable {
      if(row + col == 2){
        for(i <- 0 to 2){
          if (currentPlayerIndex == 0) {
            if(!gameboard.fields.field(2-i, i).isSet) break
            if (gameboard.fields.field(2-i, i).isSet) {
              if (!gameboard.fields.field(2-i, i).piece.get.value.equals("X")) break
            }
          } else {
            if(!gameboard.fields.field(2-i,i).isSet) break()
            if (gameboard.fields.field(2-i, i).isSet) {
              if (!gameboard.fields.field(2-i, i).piece.get.value.equals("O")) break
            }
          }
          if(i == 2){
            return true
          }
        }
      }
    }

    false
  }

  private def checkDraw():Boolean = {
    //check draw
    if(setCount == (Math.pow(3, 2) - 1)){
      return true
    }
    false
  }

  override def set(row: Int, col: Int): String = {
    setCount += 1
    if( 0 > row || 2 < row || 0 > col || 2 < col ){ //out of bounds
      return "Out of bounds!! Try it again!"
    } else if (gameboard.fields.field(row,col).isSet){//field is already set
      return "Field is already set!! Try it again!"
    }
    undoManager.doStep(new SetCommand(currentPlayerIndex, row, col, this))

    if (checkWin(row, col)) {
      if(currentPlayerIndex == 0){
        player0Score += 1
      } else {
        player1Score += 1
      }
      if(maxScore == player0Score || maxScore == player1Score) {
        publish(new GameFinishedWinner)
        createNewGame
        return ""
      }
      createNewRound
      return ""
    }
    if(checkDraw()){
      publish(new RoundFinishedDraw)
      createNewRound
      return "Sorry " + playerList(0) + " and " + playerList(1) + "! " +
        "The round ended in a tie!"}

    currentPlayerIndex = nextPlayer
    publish(new PlayerSwitch)

    publish(new FieldChanged)
    "Set your X or O with following input: s row col\n" +
      playerList(currentPlayerIndex) + "! it's your turn!"
  }

  override def gameboardToString: String = gameboard.toString

  override def undo: String = {
    if(setCount <= 0){
      return "can't do undo"
    }
    setCount -= 1
    currentPlayerIndex = nextPlayer
    undoManager.undoStep
    publish(new FieldChanged)
    publish(new PlayerSwitch)
    "undo"
  }

  override def redo: String = {
    currentPlayerIndex= nextPlayer
    undoManager.redoStep
    publish(new FieldChanged)
    publish(new PlayerSwitch)
    "redo"
  }

  override def nextState: Unit = {
    state = state.nextState()
    publish(new FieldChanged)
  }

  override def nextPlayer: Int = if (currentPlayerIndex == 0) 1 else 0

  override def load: String = ""

  override def save: String = ""
}
