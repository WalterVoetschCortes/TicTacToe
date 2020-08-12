package de.tictactoe.aview.gui

import java.awt.{Color, Font, GridBagConstraints, Insets}

import de.tictactoe.controller.controllerComponent.ControllerInterface
import javax.swing.{BorderFactory, JFrame, WindowConstants}

import scala.swing.FlowPanel.Alignment
import scala.swing.{Button, Dimension, FlowPanel, Frame, GridBagPanel, GridPanel, Label}
import java.awt.{Frame => AWTFrame}

import scala.swing.event.{ButtonClicked, MouseEntered, MouseExited, MouseReleased, MouseWheelMoved}

class Gui(controller:ControllerInterface) extends Frame {

  listenTo(controller)

  title = "TicTacToe"

  //sets screen to max
  peer.setExtendedState(AWTFrame.MAXIMIZED_BOTH)
  peer.setUndecorated(true)

  //preferredSize = new Dimension(1000,700)

  //exits program when window is closed
  peer.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)

  //Colors
  val mainColor = new Color(61,124,179)
  val blueGrey = new Color(122,159,179)

  //Fonts
  val tttFont = new Font("Calibri Light", Font.ITALIC, 30)
  val menuFont = new Font("Calibri Light", Font.BOLD, 60)

  val newGameButton = new Button{
    text = "NEW GAME"
    font = menuFont
    background = mainColor
    foreground= Color.WHITE
    focusPainted = false
    //preferredSize = new Dimension(900,200)

    this.borderPainted = false

    //hover effect:
    listenTo(mouse.moves)
    reactions += {
      case MouseEntered(_, _, _) =>
        background = blueGrey
      case MouseExited(_, _, _) =>
        background = mainColor
    }
  }

  val loadGameButton = new Button{
    text = "LOAD GAME"
    font = menuFont
    background = mainColor
    foreground= Color.WHITE
    focusPainted = false
    //preferredSize = new Dimension(900,200)

    this.borderPainted = false

    //hover effect:
    listenTo(mouse.moves)
    reactions += {
      case MouseEntered(_, _, _) =>
        background = blueGrey
      case MouseExited(_, _, _) =>
        background = mainColor
    }
  }

  val mainPanel = new GridPanel(2,1) {
    border = BorderFactory.createEmptyBorder(0,0,0,0)
    vGap = 20
    //background = mainColor

    contents += newGameButton
    contents += loadGameButton

    //preferredSize = new Dimension(1000,0)
  }

  def emptyLabel = new Label{
    text = ""
  }

  def tttLabel = new Label{
    text = "TicTacToe"
    foreground= blueGrey
    font = tttFont
    //horizontalAlignment =
  }

  val xButton = new Button{
    text = "X"
    font = tttFont
    foreground= blueGrey
    focusPainted = false

    this.opaque = false
    this.contentAreaFilled = false
    this.borderPainted = false

    //hover effect:
    listenTo(mouse.moves)
    reactions += {
      case MouseEntered(_, _, _) =>
        foreground = mainColor
      case MouseExited(_, _, _) =>
        foreground = Color.RED
    }
  }

  val belowPanel = new GridPanel(1,3) {
    contents += emptyLabel
    contents += tttLabel
    contents += xButton
  }

  val backgroundPanel = new GridBagPanel {
    border = BorderFactory.createEmptyBorder(50,50,0,50)

    add(mainPanel, new Constraints(0,0,1,1,0.0,0.0,GridBagConstraints.FIRST_LINE_START,GridBagConstraints.BOTH,
      new Insets(0,0,0,0),900,500))
    add(belowPanel, new Constraints(0,1,1,1,0.0,0.0,GridBagConstraints.FIRST_LINE_START,GridBagConstraints.BOTH,
      new Insets(10,0,0,0),0,0))
  }

  contents = backgroundPanel

  visible = true

}