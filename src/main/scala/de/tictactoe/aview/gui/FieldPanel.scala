package de.tictactoe.aview.gui

import java.awt.{Color, Font}

import de.tictactoe.controller.controllerComponent.ControllerInterface
import javax.swing.border.{LineBorder, MatteBorder}

import scala.swing.event.{ButtonClicked, MouseEntered, MouseExited}
import scala.swing.{Button, FlowPanel}

class FieldPanel (row:Int, col: Int, controller: ControllerInterface) extends Button {

  //colors:
  val mainColor = new Color(20, 189, 172)
  val grey = new Color(155, 169, 181)
  val borderColor = new Color(13, 161, 146)
  val player1Color = new Color(76, 96, 94)
  val player2Color = new Color(	242, 235, 211)

  //fonts:
  val playFont = new Font("Arial Rounded MT Bold", Font.BOLD, 40)

  text = fieldText(row,col)
  font = playFont
  background = mainColor
  focusPainted = false

  //border:
  (row, col) match {
    case (0,0) => border = new MatteBorder(0,0,5,5, borderColor)
    case (0,1) => border = new MatteBorder(0,5,5,5, borderColor)
    case (0,2) => border = new MatteBorder(0,5,5,0, borderColor)
    case (1,0) => border = new MatteBorder(5,0,5,5, borderColor)
    case (1,1) => border = new MatteBorder(5,5,5,5, borderColor)
    case (1,2) => border = new MatteBorder(5,5,5,0, borderColor)
    case (2,0) => border = new MatteBorder(5,0,0,5, borderColor)
    case (2,1) => border = new MatteBorder(5,5,0,5, borderColor)
    case (2,2) => border = new MatteBorder(5,5,0,0, borderColor)
  }

  //foreground:
  if(controller.getField.field(row,col).isSet){
    if(controller.getField.field(row,col).piece.get.value.equals("X")){
      foreground = player1Color
    }else{
      foreground = player2Color
    }
  }

  //text:
  def fieldText(row:Int, col:Int): String ={
    if(controller.getField.field(row,col).isSet){
      if(controller.getField.field(row,col).piece.get.value.equals("X")){
        "X"
      }else{
        "O"
      }
    }
    else " "
  }

  def redraw ={
    if(controller.getField.field(row,col).isSet){
      if(controller.getField.field(row,col).piece.get.value.equals("X")){
        foreground = player1Color
        text = "X"
      }else{
        foreground = player2Color
        text = "O"
      }
    }
    else text = " "
    repaint
  }

  //hover effect:
  listenTo(mouse.moves)
  reactions += {
    case MouseEntered(_, _, _) =>
      if(!controller.getField.field(row,col).isSet){
        if(controller.currentPlayerIndex == 0){
          foreground = borderColor
          text = "X"
        }else {
          foreground = borderColor
          text = "O"
        }
      }
    case MouseExited(_, _, _) =>
      if(!controller.getField.field(row,col).isSet) {
        text = " "
      }
  }

}

