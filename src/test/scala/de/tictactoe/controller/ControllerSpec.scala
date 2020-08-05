package de.tictactoe.controller

import de.tictactoe.controller.controllerComponent.controllerBaseImpl.Controller
import de.tictactoe.model.gameboardComponent.gameboardBaseImpl.{Gameboard}
import org.scalatest.{Matchers, WordSpec}

class ControllerSpec extends WordSpec with Matchers{

  "A Controller" when {
    "created" should {

      val gameboard = new Gameboard(false)
      val controller = new Controller(gameboard)

      "can handle" in {
        controller.handle("player1 player2") should be("")
      }
      "can set players with wrong input" in {
        controller.setPlayers("player1 player2 player3") should be("Wrong input!! Try it again!")
      }
      "can set players" in {
        controller.setPlayers("player1 player2") should be("")
      }
      "can create empty game board" in {
        controller.createEmptyGameboard should be("")
      }
      "can set X or O in game board with player1" in {
        controller.set(0,0) should be ("Set your X or O with following input: s row col\nplayer2! it's your turn!")
      }
      "can set X or O in a occupied field and don't permit it" in{
        controller.set(0,0) should be ("Field is already set!! Try it again!")
      }
      "can set X or O in a field out of bounds and don't permit it" in{
        controller.set(8,5) should be ("Out of bounds!! Try it again!")
      }
      "can set X or O in game board with player2" in {
        controller.set(0,1) should be ("Set your X or O with following input: s row col\nplayer1! it's your turn!")
      }
      "can create empty game board again" in {
        controller.createEmptyGameboard should be("")
      }
      "and set again new players p1 and p2" in {
        controller.setPlayers("p1 p2")
      }
      "p1 sets X to field(0,1)" in {
        controller.set(0,1) should be ("Set your X or O with following input: s row col\np2! it's your turn!")
      }
      "p2 sets O to field(0,0)" in {
        controller.set(0,0) should be ("Set your X or O with following input: s row col\np1! it's your turn!")
      }
      "p1 sets X to field(0,2)" in {
        controller.set(0,2) should be ("Set your X or O with following input: s row col\np2! it's your turn!")
      }
      "p2 sets O to field(1,1)" in {
        controller.set(1,1) should be ("Set your X or O with following input: s row col\np1! it's your turn!")
      }
      "p1 sets X to field(2,1)" in {
        controller.set(2,1) should be ("Set your X or O with following input: s row col\np2! it's your turn!")
      }
      "p2 sets O to field(2,2) and wins" in {
        controller.set(2,2) should be ("Please enter your names like (player1 player2) to start a new game!")
      }
      "can create empty game board and again" in {
        controller.createEmptyGameboard should be("")
      }
      "and set again new players done and carlo" in {
        controller.setPlayers("done carlo")
      }
      "done sets X to field(0,2)" in {
        controller.set(0,2) should be ("Set your X or O with following input: s row col\ncarlo! it's your turn!")
      }
      "carlo sets O to field(0,1)" in {
        controller.set(0,1) should be ("Set your X or O with following input: s row col\ndone! it's your turn!")
      }
      "done sets X to field(1,1)" in {
        controller.set(1,1) should be ("Set your X or O with following input: s row col\ncarlo! it's your turn!")
      }
      "carlo sets O to field(2,1)" in {
        controller.set(2,1) should be ("Set your X or O with following input: s row col\ndone! it's your turn!")
      }
      "done sets X to field(2,0) and wins" in {
        controller.set(2,0) should be ("Please enter your names like (player1 player2) to start a new game!")
      }
      "can create empty game board and again and again" in {
        controller.createEmptyGameboard should be("")
      }
      "and set again new players p3 and p4" in {
        controller.setPlayers("p3 p4")
      }
      "p3 sets X to field(0,0)" in {
        controller.set(0,0) should be ("Set your X or O with following input: s row col\np4! it's your turn!")
      }
      "p4 sets O to field(0,2)" in {
        controller.set(0,2) should be ("Set your X or O with following input: s row col\np3! it's your turn!")
      }
      "p3 sets X to field(1,0)" in {
        controller.set(1,0) should be ("Set your X or O with following input: s row col\np4! it's your turn!")
      }
      "p4 sets O to field(1,1)" in {
        controller.set(1,1) should be ("Set your X or O with following input: s row col\np3! it's your turn!")
      }
      "p3 sets X to field(0,1)" in {
        controller.set(0,1) should be ("Set your X or O with following input: s row col\np4! it's your turn!")
      }
      "p4 sets O to field(2,0) and wins" in {
        controller.set(2,0) should be ("Please enter your names like (player1 player2) to start a new game!")
      }
      "can create empty game board and again and again and again" in {
        controller.createEmptyGameboard should be("")
      }
      "and set again new players d and c" in {
        controller.setPlayers("d c")
      }
      "d sets X to field(0,0)" in {
        controller.set(0,0) should be ("Set your X or O with following input: s row col\nc! it's your turn!")
      }
      "c sets O to field(0,2)" in {
        controller.set(0,2) should be ("Set your X or O with following input: s row col\nd! it's your turn!")
      }
      "d sets X to field(1,1)" in {
        controller.set(1,1) should be ("Set your X or O with following input: s row col\nc! it's your turn!")
      }
      "c sets O to field(2,2)" in {
        controller.set(2,2) should be ("Set your X or O with following input: s row col\nd! it's your turn!")
      }
      "d sets X to field(1,2)" in {
        controller.set(1,2) should be ("Set your X or O with following input: s row col\nc! it's your turn!")
      }
      "c sets O to field(1,0)" in {
        controller.set(1,0) should be ("Set your X or O with following input: s row col\nd! it's your turn!")
      }
      "d sets X to field(2,1)" in {
        controller.set(2,1) should be ("Set your X or O with following input: s row col\nc! it's your turn!")
      }
      "c sets O to field(0,1)" in {
        controller.set(0,1) should be ("Sorry d and c! The game ended in a tie!\n\nPlease enter your names like (player1 player2) to start a new game!")
      }
    }
  }
}