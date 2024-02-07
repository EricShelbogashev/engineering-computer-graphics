package ru.nsu.e.shelbogashev.art.studio.paint.etc.tools

import java.awt.Color
import java.awt.Graphics2D
import java.awt.Point
import java.awt.image.BufferedImage
import kotlin.math.cos
import kotlin.math.sin

/**
 * Инструмент для рисования звезды на изображении.
 */
class StarTool(
    private val outerRadius: Int = 50,
    private val innerRadius: Int = 25,
    private val angle: Int = 0,
    private val angleCount: Int = 5
) {

    /**
     * Метод для рисования звезды на изображении.
     *
     * @param image Изображение, на котором рисуется звезда.
     * @param center Центр звезды (точка, вокруг которой строится звезда).
     * @param color Цвет, которым рисуется звезда.
     */
    fun draw(image: BufferedImage, center: Point, color: Color?) {
        val g2d = image.graphics as Graphics2D

        // Массивы для хранения координат точек звезды
        val xCoords = IntArray(angleCount * 2)
        val yCoords = IntArray(angleCount * 2)

        // Рассчитываем координаты точек звезды
        for (i in 0 until angleCount * 2) {
            if (i % 2 == 0) {
                // Большие лучи
                xCoords[i] =
                    (center.x + outerRadius * cos(-Math.PI / 2 + angle / 180.0 * Math.PI + (i * 2 * Math.PI) / (2 * angleCount))).toInt()
                yCoords[i] =
                    (center.y + outerRadius * sin(-Math.PI / 2 + angle / 180.0 * Math.PI + (i * 2 * Math.PI) / (2 * angleCount))).toInt()
            } else {
                // Малые лучи
                xCoords[i] =
                    (center.x + innerRadius * cos(-Math.PI / 2 + angle / 180.0 * Math.PI + (i * 2 * Math.PI) / (2 * angleCount))).toInt()
                yCoords[i] =
                    (center.y + innerRadius * sin(-Math.PI / 2 + angle / 180.0 * Math.PI + (i * 2 * Math.PI) / (2 * angleCount))).toInt()
            }
        }

        // Устанавливаем цвет и рисуем полигон
        g2d.color = color
        g2d.drawPolygon(xCoords, yCoords, angleCount * 2)
    }
}