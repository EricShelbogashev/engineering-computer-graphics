package ru.nsu.e.shelbogashev.art.studio.paint.tools

import java.awt.Color
import java.awt.Graphics2D
import java.awt.Point
import java.awt.image.BufferedImage
import kotlin.math.cos
import kotlin.math.sin

/**
 * Инструмент для рисования полигона с заданным радиусом, углом и количеством углов.
 */
class PolygonTool {
    // Параметры полигона (по умолчанию)
    var radius = 70
    var angle = 0
    var angleCount = 5

    /**
     * Метод для рисования полигона на изображении в заданный цвет.
     * @param image Изображение, на котором рисуем полигон.
     * @param centre Центр полигона.
     * @param curColor Цвет для рисования полигона.
     */
    fun draw(image: BufferedImage, centre: Point, curColor: Color?) {
        val g2d = image.graphics as Graphics2D

        // Массивы для хранения координат вершин полигона
        val xCoords = IntArray(angleCount)
        val yCoords = IntArray(angleCount)

        // Вычисляем координаты вершин полигона
        for (i in 0 until angleCount) {
            xCoords[i] = (centre.x + radius * cos(angle + (i * 2 * Math.PI) / angleCount)).toInt()
            yCoords[i] = (centre.y + radius * sin(angle + (i * 2 * Math.PI) / angleCount)).toInt()
        }

        // Устанавливаем цвет для рисования полигона
        g2d.color = curColor

        // Рисуем полигон
        g2d.drawPolygon(xCoords, yCoords, angleCount)
    }
}
