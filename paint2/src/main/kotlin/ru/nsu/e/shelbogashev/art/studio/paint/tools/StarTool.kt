package ru.nsu.e.shelbogashev.art.studio.paint.tools

import java.awt.Color
import java.awt.Graphics2D
import java.awt.Point
import java.awt.image.BufferedImage
import kotlin.math.cos
import kotlin.math.sin

/**
 * Инструмент для рисования звезды на изображении.
 */
class StarTool {
    /** Большой радиус звезды. */
    var bigRadius = 50

    /** Малый радиус звезды. */
    var smallRadius = 20

    /** Угол поворота звезды. */
    var angle = 0

    /** Количество лучей звезды. */
    var angleCount = 5

    /**
     * Метод для рисования звезды на изображении.
     *
     * @param image Изображение, на котором рисуется звезда.
     * @param centre Центр звезды (точка, вокруг которой строится звезда).
     * @param curColor Цвет, которым рисуется звезда.
     */
    fun draw(image: BufferedImage, centre: Point, curColor: Color?) {
        val g2d = image.graphics as Graphics2D

        // Массивы для хранения координат точек звезды
        val xCoords = IntArray(angleCount * 2)
        val yCoords = IntArray(angleCount * 2)

        // Рассчитываем координаты точек звезды
        for (i in 0 until angleCount * 2) {
            if (i % 2 == 0) {
                // Большие лучи
                xCoords[i] = (centre.x + bigRadius * cos(angle + (i * 2 * Math.PI) / (2 * angleCount))).toInt()
                yCoords[i] = (centre.y + bigRadius * sin(angle + (i * 2 * Math.PI) / (2 * angleCount))).toInt()
            } else {
                // Малые лучи
                xCoords[i] = (centre.x + smallRadius * cos(angle + (i * 2 * Math.PI) / (2 * angleCount))).toInt()
                yCoords[i] = (centre.y + smallRadius * sin(angle + (i * 2 * Math.PI) / (2 * angleCount))).toInt()
            }
        }

        // Устанавливаем цвет и рисуем полигон
        g2d.color = curColor
        g2d.drawPolygon(xCoords, yCoords, angleCount * 2)
    }
}