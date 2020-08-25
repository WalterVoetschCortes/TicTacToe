package de.tictactoe.aview.gui

import java.awt.event.ActionListener
import java.awt.event.ActionEvent
import java.awt.Color
import javax.swing.Timer
import scala.swing.Label

class BlinkLabel extends Label with ActionListener {

  //protected var text: String = _

  protected var textVisible: Boolean = _

  protected var origForegroundColor: Color = this.foreground

  protected var isBlink: Boolean = true

  protected var speed: Int = 500

  protected var timer: Timer = new Timer(this.speed, this)

  timer.start()

  def setForeground(color: Color): Unit = {
    this.origForegroundColor = color
  }

  def setSpeed(speed: Int): Unit = {
    this.speed = speed
  }

  def startBlinking(): Unit = {
    timer.start()
  }

  def stopBlinking(): Unit = {
    timer.stop()
    this.setForeground(origForegroundColor)
  }

  def isBlinking(): Boolean = (this.isBlink)

  def actionPerformed(e: ActionEvent): Unit = {
    this.textVisible = !this.textVisible
    this.setForeground(
      if (textVisible) origForegroundColor else this.background)
  }
}