package de.tictactoe.aview.gui

import java.awt.{Color, Font, GridBagConstraints, Insets}

import de.tictactoe.controller.controllerComponent.{ControllerInterface, NewRound, PlayerChanged, PlayerSwitch}
import javax.swing.{BorderFactory, WindowConstants}

import scala.swing.{Button, Dimension, Frame, GridBagPanel, GridPanel, Label, Slider, TextField}
import java.awt.{Frame => AWTFrame}
import java.io.File

import scala.swing.event.{ButtonClicked, MouseEntered, MouseExited}
import javax.sound.sampled._

import scala.collection.mutable

class Gui(controller:ControllerInterface) extends Frame {

  listenTo(controller)

  title = "TicTacToe"

  //sets screen to max:
  peer.setExtendedState(AWTFrame.MAXIMIZED_BOTH)
  peer.setUndecorated(true)

  //exits program when window is closed:
  peer.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)

  //fields of tic-tac-toe matrix:
  var fields = Array.ofDim[FieldPanel](3, 3)

  //music sound:
  val resourcesPathMusic = getClass.getResource("/MusicL.wav")
  val fileMusic = new File(resourcesPathMusic.getFile)
  val audioInMusic = AudioSystem.getAudioInputStream(fileMusic)
  val clipMusic = AudioSystem.getClip
  clipMusic.open(audioInMusic)
  clipMusic.loop(Clip.LOOP_CONTINUOUSLY)

  var soundIsOn = true

  //button sound effects:
  val resourcesPathSound = getClass.getResource("/ButtonHoverSound.wav")
  val fileSound = new File(resourcesPathSound.getFile)
  val audioInSound = AudioSystem.getAudioInputStream(fileSound)
  val clipSound = AudioSystem.getClip
  clipSound.open(audioInSound)

  //colors:
  val mainColor = new Color(61,124,179)
  val blueGrey = new Color(122,159,179)

  //fonts:
  val tttFont = new Font("Calibri Light", Font.ITALIC, 30)
  val xFont = new Font("Calibri Light", Font.BOLD, 30)
  val menuFont = new Font("Calibri Light", Font.BOLD, 60)
  val playersFont = new Font("Calibri Light", Font.BOLD, 20)
  val setNamesFont = new Font("Calibri Light", Font.PLAIN, 20)

  //-----------------------------------------------------------------------
  //------------------------------- --------------------first screen (menu with new game or load game):
  val newGameButton = new Button{
    text = "NEW GAME"
    font = menuFont
    background = mainColor
    foreground= Color.WHITE
    focusPainted = false

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

    contents += newGameButton
    contents += loadGameButton
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

  val firstScreenPanel = new GridBagPanel {
    border = BorderFactory.createEmptyBorder(50,50,0,50)

    add(abovePanel, new Constraints(0,0,1,1,0.0,0.0,GridBagConstraints.FIRST_LINE_START,GridBagConstraints.BOTH,
      new Insets(0,0,10,0),0,0))
    add(mainPanel, new Constraints(0,1,1,1,0.0,0.0,GridBagConstraints.FIRST_LINE_START,GridBagConstraints.BOTH,
      new Insets(0,0,0,0),900,500))
    add(belowPanel, new Constraints(0,2,1,1,0.0,0.0,GridBagConstraints.FIRST_LINE_START,GridBagConstraints.BOTH,
      new Insets(10,0,0,0),0,0))
  }

  //---------------------------------------------------------------------------------
  //-----------------------------------------------------------second screen (set player names and rounds):

  val player1TextField = new TextField("", 10){
    foreground= blueGrey
    font = setNamesFont
    border = BorderFactory.createEmptyBorder(0,10,0,0)
    preferredSize = new Dimension(100,50)
  }

  val player2TextField = new TextField("", 10){
    foreground= blueGrey
    font = setNamesFont
    border = BorderFactory.createEmptyBorder(0,10,0,0)
    preferredSize = new Dimension(100,50)
  }

  def setNamesPanel = new GridPanel(2,2){
    border = BorderFactory.createEmptyBorder(50,0,50,50)
    background = mainColor
    contents += new Label {
      text = "Player 1:"
      foreground= Color.WHITE
      font = playersFont
    }
    contents += player1TextField
    vGap = 10
    contents += new Label {
      text = "Player 2:"
      foreground= Color.WHITE
      font = playersFont
    }
    contents += player2TextField
  }

  //slider to choose number of rounds
  val slider = new Slider{
    border = BorderFactory.createEmptyBorder(0,0,0,50)
    background = mainColor
    labels = Map(1-> new Label{text = "1"; foreground= Color.WHITE},2-> new Label{text = "2"; foreground= Color.WHITE},
            3-> new Label{text = "3"; foreground= Color.WHITE},4-> new Label{text = "4"; foreground= Color.WHITE},
            5-> new Label{text = "5"; foreground= Color.WHITE},6-> new Label{text = "6"; foreground= Color.WHITE},
            7-> new Label{text = "7"; foreground= Color.WHITE},8-> new Label{text = "8"; foreground= Color.WHITE},
            9-> new Label{text = "9"; foreground= Color.WHITE}, 10-> new Label{text = "10"; foreground= Color.WHITE})
    min = 1
    max = 10
    value = 1

    minorTickSpacing = 1
    majorTickSpacing = 10
    paintTicks = true
    //paintTrack = true
    paintLabels = true
    snapToTicks = true

    foreground = Color.WHITE

    visible = true
  }

  val playButton = new Button{
    background = mainColor
    text = "play"
    foreground = Color.WHITE
    focusPainted = false
    font = xFont

    this.opaque = false
    this.contentAreaFilled = false
    this.borderPainted = false

    //hover effect:
    listenTo(mouse.moves)
    reactions += {
      case MouseEntered(_, _, _) =>
        foreground = blueGrey
      case MouseExited(_, _, _) =>
        foreground = Color.WHITE
    }
  }

  def sliderPanel = new GridPanel(2,2){
    background = mainColor
    contents += new Label {
      text = "number of rounds:"
      foreground= Color.WHITE
      font = playersFont
    }
    vGap = 10
    contents += slider
    contents += emptyLabel
    contents += playButton
  }

  val secondSmainPanel = new GridPanel(2,1) {
    border = BorderFactory.createEmptyBorder(0,0,0,0)
    vGap = 10
    background = mainColor

    contents += setNamesPanel
    contents += sliderPanel
  }

  val secondSxButton = new Button{
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
  listenTo(secondSxButton)
  reactions += {
    case ButtonClicked(`secondSxButton`) =>
      System.exit(0)
  }

  val secondSabovePanel = new GridPanel(1,3) {
    contents += emptyLabel
    contents += tttLabel
    contents += secondSxButton
  }

  val secondSbackButton = new Button{
    text = "back"
    foreground = blueGrey
    focusPainted = false
    font = xFont

    this.opaque = false
    this.contentAreaFilled = false
    this.borderPainted = false

    //hover effect:
    listenTo(mouse.moves)
    reactions += {
      case MouseEntered(_, _, _) =>
        foreground = mainColor
      case MouseExited(_, _, _) =>
        foreground = blueGrey
    }
  }

  val secondSaudioButton = new Button{
    text = "\uD83D\uDD0A"
    foreground = blueGrey
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
  listenTo(secondSaudioButton)
  reactions += {
    case ButtonClicked(`secondSaudioButton`) =>
      if(soundIsOn){
        clipMusic.stop
        secondSaudioButton.text = "\uD83D\uDD07"
        soundIsOn = false
      } else{
        clipMusic.loop(Clip.LOOP_CONTINUOUSLY)
        secondSaudioButton.text = "\uD83D\uDD0A"
        soundIsOn = true
      }
  }

  val secondSbelowPanel = new GridPanel(1,3) {
    contents += secondSbackButton
    contents += emptyLabel
    contents += secondSaudioButton
  }

  val secondScreenPanel = new GridBagPanel {
    border = BorderFactory.createEmptyBorder(50,50,0,50)

    add(secondSabovePanel, new Constraints(0,0,1,1,0.0,0.0,GridBagConstraints.FIRST_LINE_START,GridBagConstraints.BOTH,
      new Insets(0,0,10,0),0,0))
    add(secondSmainPanel, new Constraints(0,1,1,1,0.0,0.0,GridBagConstraints.FIRST_LINE_START,GridBagConstraints.BOTH,
      new Insets(0,0,0,0),900,500))
    add(secondSbelowPanel, new Constraints(0,2,1,1,0.0,0.0,GridBagConstraints.FIRST_LINE_START,GridBagConstraints.BOTH,
      new Insets(10,0,0,0),0,0))
  }

  //--------------------------------------------------------------------------------------
  //-----------------------------------------------------------third screen (play tic-tac-toe):

  def matchfieldPanel = new GridPanel(3,3){
    background = mainColor
    for{
      row <- 0 until 3
      col <- 0 until 3
    }{
      val fieldPanel = new FieldPanel(row, col, controller)
      fields(row)(col) = fieldPanel
      contents += fieldPanel

      listenTo(fieldPanel)
      reactions += {
        case ButtonClicked(`fieldPanel`) =>
          listenTo(controller)
          controller.handle("s" + row.toString + col.toString)
          fieldPanel.redraw
      }
    }
  }

  def redraw: Unit = {
    for {
      row <- 0 until 3
      column <- 0 until 3
    } fields(row)(column).redraw

    repaint
  }

  val turnInfoLabel = new Label{
    text = "player1, it's your turn!"
    background = mainColor
    foreground= Color.WHITE
    font = xFont
  }

  val player1InfoLabel = new Label{
    text = "player1 Label"
    background = mainColor
    foreground= Color.WHITE
    font = xFont
  }

  val player2InfoLabel = new Label{
    text = "player2 Label"
    background = mainColor
    foreground= Color.WHITE
    font = xFont
  }

  def infoPanel = new GridPanel(2,2){
    border = BorderFactory.createEmptyBorder(0,0,0,0)
    background = mainColor

    contents += player1InfoLabel
    contents += player2InfoLabel
    contents += turnInfoLabel
  }

  val thirdSmainPanel = new GridPanel(2,1) {
    border = BorderFactory.createEmptyBorder(0,0,0,0)
    vGap = 10
    background = mainColor

    contents += matchfieldPanel
    contents += infoPanel
  }

  val thirdSxButton = new Button{
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
  listenTo(thirdSxButton)
  reactions += {
    case ButtonClicked(`thirdSxButton`) =>
      System.exit(0)
  }

  val thirdSabovePanel = new GridPanel(1,3) {
    contents += emptyLabel
    contents += tttLabel
    contents += thirdSxButton
  }

  val thirdSaudioButton = new Button{
    text = "\uD83D\uDD0A"
    foreground = blueGrey
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
  listenTo(thirdSaudioButton)
  reactions += {
    case ButtonClicked(`thirdSaudioButton`) =>
      if(soundIsOn){
        clipMusic.stop
        thirdSaudioButton.text = "\uD83D\uDD07"
        soundIsOn = false
      } else{
        clipMusic.loop(Clip.LOOP_CONTINUOUSLY)
        thirdSaudioButton.text = "\uD83D\uDD0A"
        soundIsOn = true
      }
  }

  val thirdSbelowPanel = new GridPanel(1,3) {
    contents += emptyLabel
    contents += emptyLabel
    contents += thirdSaudioButton
  }

  val thirdScreenPanel = new GridBagPanel {
    border = BorderFactory.createEmptyBorder(50,50,0,50)

    add(thirdSabovePanel, new Constraints(0,0,1,1,0.0,0.0,GridBagConstraints.FIRST_LINE_START,GridBagConstraints.BOTH,
      new Insets(0,0,10,0),0,0))
    add(thirdSmainPanel, new Constraints(0,1,1,1,0.0,0.0,GridBagConstraints.FIRST_LINE_START,GridBagConstraints.BOTH,
      new Insets(0,0,0,0),900,500))
    add(thirdSbelowPanel, new Constraints(0,2,1,1,0.0,0.0,GridBagConstraints.FIRST_LINE_START,GridBagConstraints.BOTH,
      new Insets(10,0,0,0),0,0))
  }

  //--------------------------------------------------------------------------------------
  //listen to buttons:

  //of first screen:
  listenTo(newGameButton)
  reactions += {
    case ButtonClicked(`newGameButton`) =>
      listenTo(controller)
      controller.createNewGame

      //update audio:
      if(!soundIsOn){
        secondSaudioButton.text = "\uD83D\uDD07"
      } else{
        secondSaudioButton.text = "\uD83D\uDD0A"
      }

      //new Screen:
      contents = secondScreenPanel

      //sets screen to max
      peer.setExtendedState(AWTFrame.MAXIMIZED_BOTH)
      //peer.setUndecorated(true)
  }

  //of second screen:
  listenTo(secondSbackButton)
  reactions += {
    case ButtonClicked(`secondSbackButton`) =>
      //update audio:
      if(!soundIsOn){
        audioButton.text = "\uD83D\uDD07"
      } else{
        audioButton.text = "\uD83D\uDD0A"
      }

      //new Screen:
      contents = firstScreenPanel

      //sets screen to max
      peer.setExtendedState(AWTFrame.MAXIMIZED_BOTH)
    //peer.setUndecorated(true)
  }

  listenTo(playButton)
  reactions += {
    case ButtonClicked(`playButton`) =>
      if(!player1TextField.text.isEmpty || !player2TextField.text.isEmpty){
        listenTo(controller)
        controller.handle(player1TextField.text + " " + player2TextField.text)
        controller.handle(slider.value.toString)

        //update audio:
        if(!soundIsOn){
          thirdSaudioButton.text = "\uD83D\uDD07"
        } else{
          thirdSaudioButton.text = "\uD83D\uDD0A"
        }

        //new Screen:
        contents = thirdScreenPanel

        //sets screen to max
        peer.setExtendedState(AWTFrame.MAXIMIZED_BOTH)
        //peer.setUndecorated(true)

        repaint
      }
  }

  //-----------------
  //reactions:
  reactions += {
    case event: PlayerChanged =>
      //update infoLabels in third screen:
      player1InfoLabel.text = "X - " + controller.playerList(0).name + " - " + controller.player0Score.toString
      player2InfoLabel.text = "O - " + controller.playerList(1).name + " - " + controller.player1Score.toString
      turnInfoLabel.text = controller.playerList(controller.currentPlayerIndex).name + ", it's your turn!"
      repaint

    case event: PlayerSwitch =>
      //update infoLabel in third screen:
      turnInfoLabel.text = controller.playerList(controller.currentPlayerIndex).name + ", it's your turn!"

    case event: NewRound =>
      //update infoLabels in third screen:
      player1InfoLabel.text = "X - " + controller.playerList(0).name + " - " + controller.player0Score.toString
      player2InfoLabel.text = "O - " + controller.playerList(1).name + " - " + controller.player1Score.toString

      //clear fields:

  }


  contents = firstScreenPanel

  visible = true

}