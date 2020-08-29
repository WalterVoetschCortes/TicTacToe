package de.tictactoe.aview.gui

import java.awt.event.ActionEvent
import java.awt.{Color, Font, Graphics, Graphics2D, GridBagConstraints, Insets, Frame => AWTFrame}

import de.tictactoe.controller.controllerComponent.{ControllerInterface, GameFinishedWinner, NewRound, PlayerChanged, PlayerSwitch, RoundFinishedDraw, RoundFinishedWin}
import javax.swing.{BorderFactory, ImageIcon, JLabel, Timer, WindowConstants}

import scala.swing.{BorderPanel, Button, Dimension, FlowPanel, Frame, GridBagPanel, GridPanel, Label, Panel, Slider, TextField}
import java.io.File

import scala.swing.event.{ButtonClicked, MouseEntered, MouseExited}
import javax.sound.sampled._
import java.awt.event.ActionListener

import javax.imageio.ImageIO

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

  //win sound:
  val resourcesPathWin = getClass.getResource("/DiscoLoop.wav")
  val fileMusicWin = new File(resourcesPathWin.getFile)
  val audioInWin = AudioSystem.getAudioInputStream(fileMusicWin)
  val clipMusicWin = AudioSystem.getClip
  clipMusicWin.open(audioInWin)

  //button sound effects:
  val resourcesPathSound = getClass.getResource("/ButtonHoverSound.wav")
  val fileSound = new File(resourcesPathSound.getFile)
  val audioInSound = AudioSystem.getAudioInputStream(fileSound)
  val clipSound = AudioSystem.getClip
  clipSound.open(audioInSound)

  //win round sound effects:
  val resourcesPathSoundRound = getClass.getResource("/RoundWinSound.wav")
  val fileSoundRound = new File(resourcesPathSoundRound.getFile)
  val audioInSoundRound = AudioSystem.getAudioInputStream(fileSoundRound)
  val clipSoundRound = AudioSystem.getClip
  clipSoundRound.open(audioInSoundRound)

  //congratulation gif:
  //val fireworks = ImageIO.read(getClass.getResource("/Confetti.gif"))
  val fireworksGif = new ImageIcon(getClass.getResource("/Taddel.gif"))

  //confetti gif:
  val confettiGif = new ImageIcon(getClass.getResource("/Taddel.gif"))

  //colors:
  val mainColor = new Color(20, 189, 172)
  val grey = new Color(155, 169, 181)

  //fonts:
  val tttFont = new Font("Calibri Light", Font.ITALIC, 30)
  val xFont = new Font("Calibri Light", Font.BOLD, 30)
  val menuFont = new Font("Calibri Light", Font.BOLD, 60)
  val playersFont = new Font("Calibri Light", Font.BOLD, 30)
  val setNamesFont = new Font("Calibri Light", Font.PLAIN, 20)

  //-----------------------------------------------------------------------
  //------------------------------- --------------------first screen (menu with new game or load game):
  val newGameButton = new Button{
    //border = BorderFactory.createEmptyBorder(50,50,0,50)
    border = new RoundedBorder(mainColor,2,16,0)
    text = "NEW GAME"
    font = menuFont
    background = mainColor
    foreground= Color.WHITE
    focusPainted = false

    //this.borderPainted = false

    //hover effect:
    listenTo(mouse.moves)
    reactions += {
      case MouseEntered(_, _, _) =>
        clipSound.loop(1)
        background = grey
      case MouseExited(_, _, _) =>
        background = mainColor
    }
  }

  val loadGameButton = new Button{
    //border = BorderFactory.createEmptyBorder(0,50,50,50)
    border = new RoundedBorder(mainColor,2,16,0)
    text = "LOAD GAME"
    font = menuFont
    background = mainColor
    foreground= Color.WHITE
    focusPainted = false

    //this.borderPainted = false

    //hover effect:
    listenTo(mouse.moves)
    reactions += {
      case MouseEntered(_, _, _) =>
        clipSound.loop(1)
        background = grey
      case MouseExited(_, _, _) =>
        background = mainColor
    }
  }

  val mainPanel = new GridBagPanel{
    border = new RoundedBorder(grey,2,16,0)
    add(newGameButton,new Constraints(0,0,1,1,1.0,1.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,
      new Insets(30,30,10,30),0,0))
    add(loadGameButton,new Constraints(0,1,1,1,1.0,1.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,
      new Insets(0,30,30,30),0,0))
  }

  def emptyLabel = new Label{
    text = ""
  }

  def tttLabel = new Label{
    text = "TicTacToe"
    foreground= grey
    font = tttFont
    //horizontalAlignment =
  }

  val xButton = new Button{
    text = "X"
    font = xFont
    foreground= grey
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
        foreground = grey
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
    foreground= grey
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
        foreground = grey
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
    foreground= grey
    font = setNamesFont
    border = BorderFactory.createEmptyBorder(0,10,0,0)
    preferredSize = new Dimension(100,50)
  }

  val player2TextField = new TextField("", 10){
    foreground= grey
    font = setNamesFont
    border = BorderFactory.createEmptyBorder(0,10,0,0)
    preferredSize = new Dimension(100,50)
  }

  def setNamesPanel = new GridPanel(2,2){
    //border = BorderFactory.createEmptyBorder(50,0,50,50)
    //background = mainColor
    contents += new Label {
      text = "Player 1:"
      foreground= mainColor
      font = playersFont
    }
    contents += player1TextField
    vGap = 10
    contents += new Label {
      text = "Player 2:"
      foreground= mainColor
      font = playersFont
    }
    contents += player2TextField
  }

  //slider to choose number of rounds
  val slider = new Slider{
    //border = BorderFactory.createEmptyBorder(0,0,0,50)
    //background = mainColor
    labels = Map(1-> new Label{text = "1"; foreground= mainColor},2-> new Label{text = "2"; foreground= mainColor},
            3-> new Label{text = "3"; foreground= mainColor},4-> new Label{text = "4"; foreground= mainColor},
            5-> new Label{text = "5"; foreground= mainColor},6-> new Label{text = "6"; foreground= mainColor},
            7-> new Label{text = "7"; foreground= mainColor},8-> new Label{text = "8"; foreground= mainColor},
            9-> new Label{text = "9"; foreground= mainColor}, 10-> new Label{text = "10"; foreground= mainColor})
    min = 1
    max = 10
    value = 1

    minorTickSpacing = 1
    majorTickSpacing = 10
    paintTicks = true
    //paintTrack = true
    paintLabels = true
    snapToTicks = true

    foreground = mainColor

    visible = true
  }

  val playButton = new Button{
    border = new RoundedBorder(mainColor,2,16,0)
    background = mainColor
    text = "play"
    foreground = Color.WHITE
    focusPainted = false
    font = xFont

    //this.opaque = false
    //this.contentAreaFilled = false
    //this.borderPainted = false

    //hover effect:
    listenTo(mouse.moves)
    reactions += {
      case MouseEntered(_, _, _) =>
        clipSound.loop(1)
        background = grey
      case MouseExited(_, _, _) =>
        background = mainColor
    }
  }

  def sliderPanel = new GridPanel(2,2){
    //background = mainColor
    contents += new Label {
      text = "number of rounds:"
      foreground= mainColor
      font = playersFont
    }
    vGap = 10
    contents += slider
    contents += emptyLabel
    contents += playButton
  }

  val secondSmainPanel = new GridBagPanel {
    border = new RoundedBorder(grey,2,16,0)

    add(setNamesPanel,new Constraints(0,0,1,1,1.0,1.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,
      new Insets(30,30,10,30),0,0))
    add(sliderPanel,new Constraints(0,1,1,1,1.0,1.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,
      new Insets(0,30,30,30),0,0))
  }

  val secondSxButton = new Button{
    text = "X"
    font = xFont
    foreground= grey
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
        foreground = grey
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
    foreground = grey
    focusPainted = false
    font = xFont

    this.opaque = false
    this.contentAreaFilled = false
    this.borderPainted = false

    //hover effect:
    listenTo(mouse.moves)
    reactions += {
      case MouseEntered(_, _, _) =>
        clipSound.loop(1)
        foreground = mainColor
      case MouseExited(_, _, _) =>
        foreground = grey
    }
  }

  val secondSaudioButton = new Button{
    text = "\uD83D\uDD0A"
    foreground = grey
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
        foreground = grey
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
    //border = BorderFactory.createEmptyBorder(20,200,20,200)
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
    //background = mainColor
    foreground= mainColor
    font = xFont
  }

  val player1InfoName = new Label{
    text = "Player1"
    //background = mainColor
    foreground= mainColor
    font = xFont
  }

  val player1InfoScore = new Label{
    text = "0"
    //background = mainColor
    foreground= mainColor
    font = xFont
  }

  //blink effect on score text of p1
  val blinkActionListenerP1 = new ActionListener() {
    var count = 0
    private val maxCount = 8
    private var on = false
    def actionPerformed(e: ActionEvent): Unit
    =
    {
      if (count >= maxCount) {
        player1InfoScore.text = controller.player0Score.toString
        (e.getSource.asInstanceOf[Timer]).stop()
      }
      else {
        if(on){
          player1InfoScore.text= ""
        }else{
          player1InfoScore.text= controller.player0Score.toString
        }
        on = !on
        count += 1
      }
    }
  }

  val blinkTimerP1 = new Timer(200, blinkActionListenerP1)

  //blink effect on score text of p1
  val blinkActionListenerP2 = new ActionListener() {
    var count = 0
    private val maxCount = 8
    private var on = false
    def actionPerformed(e: ActionEvent): Unit
    =
    {
      if (count >= maxCount) {
        player2InfoScore.text = controller.player1Score.toString
        (e.getSource.asInstanceOf[Timer]).stop()
      }
      else {
        if(on){
          player2InfoScore.text= ""
        }else{
          player2InfoScore.text= controller.player1Score.toString
        }
        on = !on
        count += 1
      }
    }
  }

  val blinkTimerP2 = new Timer(200, blinkActionListenerP2)

  val player1InfoPanel = new GridPanel(2,1){
    contents+= player1InfoName
    contents+= player1InfoScore
  }

  val player2InfoName = new Label{
    text = "Player2"
    //background = mainColor
    foreground= mainColor
    font = xFont
  }

  val player2InfoScore = new Label{
    text = "0"
    //background = mainColor
    foreground= mainColor
    font = xFont
  }

  val player2InfoPanel = new GridPanel(2,1){
    contents+= player2InfoName
    contents+= player2InfoScore
  }

  def infoPanel = new GridBagPanel {
    add(turnInfoLabel,new Constraints(0,0,3,1,1.0,0.0,
      GridBagConstraints.CENTER,GridBagConstraints.BOTH,
      new Insets(0,0,0,0),0,0))
    add(player1InfoPanel,new Constraints(0,1,1,1,1.0,1.0,
      GridBagConstraints.CENTER,GridBagConstraints.BOTH,
      new Insets(0,0,0,0),0,0))
    add(player2InfoPanel,new Constraints(1,1,1,1,1.0,1.0,
      GridBagConstraints.CENTER,GridBagConstraints.BOTH,
      new Insets(0,0,0,0),0,0))
  }

  val thirdSmainPanel = new GridBagPanel {
    border = new RoundedBorder(grey, 2, 16, 0)

    add(matchfieldPanel, new Constraints(0, 0, 1, 1, 0.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
      new Insets(30, 0, 10, 0), 0, 0))
    add(infoPanel, new Constraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
      new Insets(0, 30, 0, 30), 0, 0))
  }

  val thirdSxButton = new Button{
    text = "X"
    font = xFont
    foreground= grey
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
        foreground = grey
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
    foreground = grey
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
        foreground = grey
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
  //-----------------------------------------------------------fourth screen (win tic-tac-toe):

  val winLabel = new Label{
    icon = fireworksGif
  }

  def confettiLabel = new Label{
    icon = confettiGif
  }

  val winnerNameLabel = new Label{
    text = "You're the winner!"
    font = playersFont
    foreground = mainColor
  }

  val fourthSmainPanel = new GridBagPanel {
    border = new RoundedBorder(grey,2,16,0)

    add(winLabel,new Constraints(0,0,1,1,0.0,1.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,
      new Insets(0,0,0,0),0,0))
    add(winnerNameLabel,new Constraints(0,1,1,1,1.0,1.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,
      new Insets(0,0,0,0),0,0))


  }

  val fourthSxButton = new Button{
    text = "X"
    font = xFont
    foreground= grey
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
        foreground = grey
    }
  }
  listenTo(fourthSxButton)
  reactions += {
    case ButtonClicked(`fourthSxButton`) =>
      System.exit(0)
  }

  val fourthSabovePanel = new GridPanel(1,3) {
    contents += emptyLabel
    contents += tttLabel
    contents += thirdSxButton
  }

  val fourthSaudioButton = new Button{
    text = "\uD83D\uDD0A"
    foreground = grey
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
        foreground = grey
    }
  }
  listenTo(fourthSaudioButton)
  reactions += {
    case ButtonClicked(`fourthSaudioButton`) =>
      if(soundIsOn){
        clipMusicWin.stop
        fourthSaudioButton.text = "\uD83D\uDD07"
        soundIsOn = false
      } else{
        clipMusicWin.loop(Clip.LOOP_CONTINUOUSLY)
        fourthSaudioButton.text = "\uD83D\uDD0A"
        soundIsOn = true
      }
  }

  val fourthSMenuButton = new Button{
    text = "MENU"
    foreground = grey
    focusPainted = false
    font = xFont

    this.opaque = false
    this.contentAreaFilled = false
    this.borderPainted = false

    //hover effect:
    listenTo(mouse.moves)
    reactions += {
      case MouseEntered(_, _, _) =>
        clipSound.loop(1)
        foreground = mainColor
      case MouseExited(_, _, _) =>
        foreground = grey
    }
  }

  val fourthSbelowPanel = new GridPanel(1,3) {
    contents += fourthSMenuButton
    contents += emptyLabel
    contents += fourthSaudioButton
  }

  val fourthScreenPanel = new GridBagPanel {
    border = BorderFactory.createEmptyBorder(50,50,0,50)

    add(fourthSabovePanel, new Constraints(0,0,1,1,0.0,0.0,GridBagConstraints.FIRST_LINE_START,GridBagConstraints.BOTH,
      new Insets(0,0,10,0),0,0))
    add(fourthSmainPanel, new Constraints(0,1,1,1,0.0,0.0,GridBagConstraints.FIRST_LINE_START,GridBagConstraints.BOTH,
      new Insets(0,0,0,0),900,500))
    add(fourthSbelowPanel, new Constraints(0,2,1,1,0.0,0.0,GridBagConstraints.FIRST_LINE_START,GridBagConstraints.BOTH,
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

  //of fourth screen:
  listenTo(fourthSMenuButton)
  reactions += {
    case ButtonClicked(`fourthSMenuButton`) =>
      //update audio:
      if(!soundIsOn){
        audioButton.text = "\uD83D\uDD07"
      } else{
        audioButton.text = "\uD83D\uDD0A"
        clipMusicWin.stop
        clipMusic.loop(Clip.LOOP_CONTINUOUSLY)
      }

      //new Screen:
      contents = firstScreenPanel

      //sets screen to max
      peer.setExtendedState(AWTFrame.MAXIMIZED_BOTH)
    //peer.setUndecorated(true)
  }


  //-----------------
  //reactions:
  reactions += {
    case event: PlayerChanged =>
      //update infoLabels in third screen:
      player1InfoName.text = controller.playerList(0).name
      player2InfoName.text = controller.playerList(1).name
      turnInfoLabel.text = controller.playerList(controller.currentPlayerIndex).name + ", it's your turn!"
      repaint

    case event: PlayerSwitch =>
      //update infoLabel in third screen:
      turnInfoLabel.text = controller.playerList(controller.currentPlayerIndex).name + ", it's your turn!"

    case event: RoundFinishedWin =>
      //round win sound effect:
      clipSoundRound.loop(2)

      //update infoLabels in third screen:
      player1InfoScore.text = controller.player0Score.toString
      player2InfoScore.text = controller.player1Score.toString

      //blink effect of score info text:
      controller.currentPlayerIndex match {
        case 0 =>
          blinkTimerP1.start()
          blinkActionListenerP1.count = 0
        case 1 =>
          blinkTimerP2.start()
          blinkActionListenerP2.count = 0
      }

    case event: NewRound =>
      //clear fields:
      for {
        row <- 0 until 3
        col <- 0 until 3
      } {
        fields(row)(col).redraw
      }

    case event: GameFinishedWinner =>
      //update winner label:
      if(controller.currentPlayerIndex == 0){
        winnerNameLabel.text = "Congratulations "+
          controller.playerList(controller.currentPlayerIndex).name.toUpperCase + "! You" +
          " won " + controller.player0Score.toString + " : " + controller.player1Score.toString + " against " +
          controller.playerList(controller.nextPlayer) + "!"
      } else{
        winnerNameLabel.text = "Congratulations "+
          controller.playerList(controller.currentPlayerIndex).name.toUpperCase() + "! You" +
          " won " + controller.player1Score.toString + " : " + controller.player0Score.toString + " against " +
          controller.playerList(controller.nextPlayer) + "!"
      }

      //clear fields:
      for {
        row <- 0 until 3
        col <- 0 until 3
      } {
        fields(row)(col).redraw
      }

      //clear scores:
      player1InfoScore.text = "0"
      player2InfoScore.text = "0"

      //update audio:
      if(!soundIsOn){
        fourthSaudioButton.text = "\uD83D\uDD07"
      } else{
        fourthSaudioButton.text = "\uD83D\uDD0A"
        clipMusic.stop
        clipMusicWin.loop(Clip.LOOP_CONTINUOUSLY)
      }

      //new Screen:
      contents = fourthScreenPanel

      //sets screen to max
      peer.setExtendedState(AWTFrame.MAXIMIZED_BOTH)
  }

  contents = firstScreenPanel

  visible = true

}