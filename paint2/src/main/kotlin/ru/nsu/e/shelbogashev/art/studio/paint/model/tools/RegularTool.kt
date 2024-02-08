package ru.nsu.e.shelbogashev.art.studio.paint.model.tools

import java.awt.Color
import java.awt.Graphics2D
import java.awt.Point
import java.awt.image.BufferedImage
import kotlin.math.cos
import kotlin.math.sin

class RegularTool(private val radius: Int = 50, private val angle: Int = 0, private val angleCount: Int = 5) {

    /**
     * Draws a polygon on the specified image in the given color.
     *
     * @param image The image on which to draw the polygon.
     * @param centre The center point of the polygon.
     * @param curColor The color to use for the polygon.
     */
    fun draw(image: BufferedImage, centre: Point, curColor: Color?) {
        image.graphics.let { g2d ->
            if (g2d is Graphics2D) {
                g2d.apply {
                    color = curColor
                    drawPolygon(calculateXCoords(centre), calculateYCoords(centre), angleCount)
                }
            }
        }
    }

    private fun calculateXCoords(centre: Point): IntArray = IntArray(angleCount) { i ->
        (centre.x + radius * cos(Math.toRadians(-90.0 - angle + (360.0 / angleCount) * i))).toInt()
    }

    private fun calculateYCoords(centre: Point): IntArray = IntArray(angleCount) { i ->
        (centre.y + radius * sin(Math.toRadians(-90.0 - angle + (360.0 / angleCount) * i))).toInt()
    }
}
