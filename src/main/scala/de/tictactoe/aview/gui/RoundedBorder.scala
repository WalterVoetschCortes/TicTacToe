package de.tictactoe.aview.gui

import java.awt.geom.{Area, RoundRectangle2D}
import java.awt.{BasicStroke, Component, Graphics, Graphics2D, Polygon, Rectangle, RenderingHints}
import javax.swing.border.AbstractBorder
import scala.swing.{Color, Insets}


class RoundedBorder(private var color: Color,
                       private var thickness: Int,
                       private var radii: Int,
                       private var pointerSize: Int)
  extends AbstractBorder {

  private var insets: Insets = new Insets(pad, pad, bottomPad, pad)

  private var stroke: BasicStroke = new BasicStroke(thickness)

  private var strokePad: Int = thickness / 2

  private var pointerPad: Int = 4

  private var left: Boolean = true

  var hints: RenderingHints = new RenderingHints(
    RenderingHints.KEY_ANTIALIASING,
    RenderingHints.VALUE_ANTIALIAS_ON)

  val pad: Int = radii + strokePad

  val bottomPad: Int = pad + pointerSize + strokePad

  def this(color: Color) = this(color, 4, 8, 7)

  def this(color: Color,
           thickness: Int,
           radii: Int,
           pointerSize: Int,
           left: Boolean) = {
    this(color, thickness, radii, pointerSize)
    this.left = left
  }

  override def getBorderInsets(c: Component): Insets = insets

  override def getBorderInsets(c: Component, insets: Insets): Insets =
    getBorderInsets(c)

  override def paintBorder(c: Component,
                           g: Graphics,
                           x: Int,
                           y: Int,
                           width: Int,
                           height: Int): Unit = {
    val g2: Graphics2D = g.asInstanceOf[Graphics2D]
    val bottomLineY: Int = height - thickness - pointerSize
    val bubble: RoundRectangle2D.Double = new RoundRectangle2D.Double(
      0 + strokePad,
      0 + strokePad,
      width - thickness,
      bottomLineY,
      radii,
      radii)
    val pointer: Polygon = new Polygon()
    if (left) {
      // left point
      pointer.addPoint(strokePad + radii + pointerPad, bottomLineY)
      // right point
      pointer.addPoint(strokePad + radii + pointerPad + pointerSize,
        bottomLineY)
      // bottom point
      pointer.addPoint(strokePad + radii + pointerPad + (pointerSize / 2),
        height - strokePad)
    } else {
      // left point
      pointer.addPoint(width - (strokePad + radii + pointerPad), bottomLineY)
      // right point
      pointer.addPoint(width - (strokePad + radii + pointerPad + pointerSize),
        bottomLineY)
      // bottom point
      pointer.addPoint(width -
        (strokePad + radii + pointerPad + (pointerSize / 2)),
        height - strokePad)
    }
    val area: Area = new Area(bubble)
    area.add(new Area(pointer))
    g2.setRenderingHints(hints)
    // of the text bubble.
    val parent: Component = c.getParent
    if (parent != null) {
      val bg: Color = parent.getBackground
      val rect: Rectangle = new Rectangle(0, 0, width, height)
      val borderRegion: Area = new Area(rect)
      borderRegion.subtract(area)
      g2.setClip(borderRegion)
      g2.setColor(bg)
      g2.fillRect(0, 0, width, height)
      g2.setClip(null)
    }
    g2.setColor(color)
    g2.setStroke(stroke)
    g2.draw(area)
  }
  // Paint the BG color of the parent, everywhere outside the clip
  // Paint the BG color of the parent, everywhere outside the clip

}