package de.tictactoe

import com.google.inject.Guice
import de.tictactoe.aview.Tui
import de.tictactoe.aview.gui.{Gui}
import de.tictactoe.controller.controllerComponent.ControllerInterface
import scala.io.StdIn.readLine

object TicTacToe {
  val injector = Guice.createInjector(new TicTacToeModule)
  val controller = injector.getInstance(classOf[ControllerInterface])
  val tui = new Tui(controller)
  val gui = new Gui(controller)


  def main(args: Array[String]): Unit = {
    println(welcome())
    var input = ""
    do {
      input = readLine()
      println(tui.processInputLine(input))
    } while (!input.equals("q"))
  }

  def welcome():String ={
    "Welcome to TicTacToe!\nPlease enter your names like (player1 player2)"
  }

}
