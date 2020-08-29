package de.tictactoe.aview.gui

import java.awt.{Color, Font, GridBagConstraints, Insets}
import de.tictactoe.controller.controllerComponent.ControllerInterface
import javax.swing.{BorderFactory, WindowConstants}
import scala.swing.{Button, Frame, GridBagPanel, GridPanel, Label}
import java.awt.{Frame => AWTFrame}
import java.io.File
import scala.swing.event.{ButtonClicked, MouseEntered, MouseExited, MouseReleased, MouseWheelMoved}
import javax.sound.sampled._


class Gui(controller:ControllerInterface) extends Frame {

  listenTo(controller)

  title = "TicTacToe"

  //sets screen to max
  peer.setExtendedState(AWTFrame.MAXIMIZED_BOTH)
  peer.setUndecorated(true)

  //preferredSize = new Dimension(1000,700)

  //exits program when window is closed
  peer.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)

  //music sound
  val resourcesPathMusic = getClass.getResource("/MusicL.wav")
  val fileMusic = new File(resourcesPathMusic.getFile)
  val audioInMusic = AudioSystem.getAudioInputStream(fileMusic)
  val clipMusic = AudioSystem.getClip
  clipMusic.open(audioInMusic)
  clipMusic.loop(Clip.LOOP_CONTINUOUSLY)

  var soundIsOn = true

  //button sound effects
  val resourcesPathSound = getClass.getResource("/ButtonHoverSound.wav")
  val fileSound = new File(resourcesPathSound.getFile)
  val audioInSound = AudioSystem.getAudioInputStream(fileSound)
  val clipSound = AudioSystem.getClip
  clipSound.open(audioInSound)

  //colors
  val mainColor = new Color(61,124,179)
  val blueGrey = new Color(122,159,179)

  //fonts
  val tttFont = new Font("Calibri Light", Font.ITALIC, 30)
  val xFont = new Font("Calibri Light", Font.BOLD, 30)
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
        clipSound.loop(1)
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
        clipSound.loop(1)
        background = blueGrey
      case MouseExited(_, _, _) =>
        background = mainColor
    }
  }

  val mainPanel = new GridPanel(2,1) {
    border = BorderFactory.createEmptyBorder(0,0,0,0)
    vGap = 10
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
  }

  val xButton = new Button{
    text = "X"
    font = xFont
    foreground= blueGrey
    focusPainted = false

    this.opaque = false
    this.contentAreaFilled = false
    this.borderPainted = false

    //hover effect:
    listenTo(mouse.moves)
    reactions += {
      case MouseEntered(_, _, _) =>
        foreground = Color.RED
      case MouseExited(_, _, _) =>
        foreground = blueGrey
    }
  }

  listenTo(xButton)
  reactions += {
    case ButtonClicked(`xButton`) =>
      System.exit(0)
  }

  val abovePanel = new GridPanel(1,3) {
    contents += emptyLabel
    contents += tttLabel
    contents += xButton
  }


  val audioButton = new Button{
    text = "\uD83D\uDD0A"
    foreground= blueGrey
    focusPainted = false
    font = font.deriveFont(1, 30)

    this.opaque = false
    this.contentAreaFilled = false
    this.borderPainted = false

    //hover effect:
    listenTo(mouse.moves)
    reactions += {
      case MouseEntered(_, _, _) =>
        foreground = Color.ORANGE
      case MouseExited(_, _, _) =>
        foreground = blueGrey
    }
  }

  listenTo(audioButton)
  reactions += {
    case ButtonClicked(`audioButton`) =>
      if(soundIsOn){
        clipMusic.stop
        audioButton.text = "\uD83D\uDD07"
        soundIsOn = false
      } else{
        clipMusic.loop(Clip.LOOP_CONTINUOUSLY)
        audioButton.text = "\uD83D\uDD0A"
        soundIsOn = true
      }
  }

  val belowPanel = new GridPanel(1,3) {
    contents += emptyLabel
    contents += emptyLabel
    contents += audioButton
  }

  val backgroundPanel = new GridBagPanel {
    border = BorderFactory.createEmptyBorder(50,50,0,50)

    add(abovePanel, new Constraints(0,0,1,1,0.0,0.0,GridBagConstraints.FIRST_LINE_START,GridBagConstraints.BOTH,
      new Insets(0,0,10,0),0,0))
    add(mainPanel, new Constraints(0,1,1,1,0.0,0.0,GridBagConstraints.FIRST_LINE_START,GridBagConstraints.BOTH,
      new Insets(0,0,0,0),900,500))
    add(belowPanel, new Constraints(0,2,1,1,0.0,0.0,GridBagConstraints.FIRST_LINE_START,GridBagConstraints.BOTH,
      new Insets(10,0,0,0),0,0))

  }

  contents = backgroundPanel

  visible = true

}