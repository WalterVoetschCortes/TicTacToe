package de.tictactoe.controller.controllerComponent.controllerBaseImpl

import com.google.inject.{Guice, Inject}
import de.tictactoe.TicTacToeModule
import de.tictactoe.controller.controllerComponent.{ControllerInterface, FieldChanged, GameFinishedDraw, GameFinishedWinner, NewGame, PlayerChanged, PlayerSwitch}
import de.tictactoe.model.gameboardComponent.GameboardInterface
import de.tictactoe.model.gameboardComponent.gameboardBaseImpl.{Field, Game, Gameboard, Matrix}
import de.tictactoe.model.playerComponent.Player
import de.tictactoe.util.UndoManager

import scala.swing.Publisher

class Controller @Inject()(var gameboard:GameboardInterface) extends ControllerInterface with Publisher {

  val injector = Guice.createInjector(new TicTacToeModule)
  //val fileIO = injector.getInstance(classOf[FileIOInterface])

  var player0 = Player("Player0")
  var player1 = Player("Player1")
  var game = Game(player0, player1, gameboard)
  var playerList = List[Player](player0,player1)
  var currentPlayerIndex: Int = 0
  private val undoManager = new UndoManager
  var state: ControllerState = EnterPlayerState(this)

  override def handle(input: String): String = {
    state.handle(input)
  }

  override def setPlayers(input: String): String = {
    playerList = game.setPlayers(input)
    nextState
    publish(new PlayerChanged)
    ""
  }

  override def createEmptyGameboard: String = {
    gameboard= new Gameboard(false)
    game = game.copy(player0, player1, gameboard)
    state = EnterPlayerState(this)
    publish(new NewGame)
    currentPlayerIndex=0
    "created new game board\nPlease enter the names like (player1 player2)"
  }

  private def checkWin(): Boolean = false

  private def checkDraw():Boolean = false

  override def set(row: Int, col: Int): String = {
    currentPlayerIndex match {
      case 0 =>
        undoManager.doStep(new SetCommand(currentPlayerIndex, row, col, this))

        if (checkWin()) {
          publish(new GameFinishedWinner)
        } else if(checkDraw()){
          publish(new GameFinishedDraw)
        }
        currentPlayerIndex = nextPlayer
        publish(new PlayerChanged)
      case 1 =>
        undoManager.doStep(new SetCommand(currentPlayerIndex, row, col, this))
        if (checkWin()) {
          publish(new GameFinishedWinner)
        } else if(checkDraw()){
          publish(new GameFinishedDraw)
        }
        currentPlayerIndex = nextPlayer
        publish(new PlayerChanged)
    }
    publish(new FieldChanged)
    "put your X or O with following input: p row col\n" +
        playerList(currentPlayerIndex) + " it's your turn!"
  }

  override def gameboardToString: String = gameboard.toString

  override def undo: String = {
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

  override def getField:Matrix[Field] = gameboard.fields

  override def load: String = ???

  override def save: String = ???
}
