package de.tictactoe.aview.gui

import java.awt.Color

import de.tictactoe.controller.controllerComponent.ControllerInterface
import javax.swing.border.LineBorder

import scala.swing.event.ButtonClicked
import scala.swing.{Button, FlowPanel}

class FieldPanel (row:Int, col: Int, controller: ControllerInterface) extends Button {

  text = fieldText(row,col)
  font = font.deriveFont(1, 25)
  foreground = Color.BLUE
  border = new LineBorder(Color.LIGHT_GRAY, 2)
  background = Color.WHITE
  focusPainted = false



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
        text = "X"
      }else{
        text = "O"
      }
    }
    else text = " "
    repaint
  }


}

