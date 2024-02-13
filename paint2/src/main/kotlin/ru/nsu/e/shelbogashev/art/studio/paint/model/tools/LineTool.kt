package ru.nsu.e.shelbogashev.art.studio.paint.model.tools

import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics2D
import java.awt.Point
import java.awt.image.BufferedImage
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

/**
 * Инструмент для рисования линий на изображении.
 */
class LineTool {
    /**
     * Метод для рисования линии на изображении.
     *
     * @param image Изображение, на котором рисуется линия.
     * @param thickness Толщина линии.
     * @param curentColor Цвет линии.
     * @param start Начальная точка линии.
     * @param end Конечная точка линии.
     */
    fun drawLine(image: BufferedImage, thickness: Int, curentColor: Color, start: Point, end: Point) {
        val g2d = image.graphics as Graphics2D

        val xStart = start.x
        val yStart = start.y
        val xEnd = end.x
        val yEnd = end.y

        if (thickness > 1) {
            // Рисование линии с учетом толщины
            g2d.color = curentColor
            g2d.stroke = BasicStroke(thickness.toFloat())
            g2d.drawLine(xStart, yStart, xEnd, yEnd)
        } else {
            // Установка цвета для начальной точки, остальные точки будут вычислены алгоритмом Брезенхема
            image.setRGB(xStart, yStart, curentColor.rgb)

            val dx = abs((xEnd - xStart).toDouble()).toInt()
            val dy = abs((yStart - yEnd).toDouble()).toInt()

            var err = -dx

            // Существует два варианта расположения линии:
            // 1) Угол между линией и Ox меньше 45 градусов
            // 2) Угол между линией и Ox больше 45 градусов
            if (dx >= dy) {
                // Первый случай: угол меньше 45 градусов
                when {
                    (xEnd - xStart) >= 0 && (yEnd - yStart) >= 0 -> {
                        var x = min(xStart.toDouble(), xEnd.toDouble()).toInt()
                        var y = min(yStart.toDouble(), yEnd.toDouble()).toInt()

                        for (i in 0 until dx) {
                            x++
                            err += 2 * dy
                            if (err > 0) {
                                y++
                                err -= 2 * dx
                            }
                            image.setRGB(x, y, curentColor.rgb)
                        }
                    }// добавить ограничение уменьшения hint
                    // разобрать с большими фото

                    (xEnd - xStart) >= 0 && (yEnd - yStart) <= 0 -> {
                        var x = min(xStart.toDouble(), xEnd.toDouble()).toInt()
                        var y = max(yStart.toDouble(), yEnd.toDouble()).toInt()

                        for (i in 0 until dx) {
                            x++
                            err += 2 * dy
                            if (err > 0) {
                                y--
                                err -= 2 * dx
                            }
                            image.setRGB(x, y, curentColor.rgb)
                        }
                    }

                    (xEnd - xStart) <= 0 && (yEnd - yStart) >= 0 -> {
                        var x = max(xStart.toDouble(), xEnd.toDouble()).toInt()
                        var y = min(yStart.toDouble(), yEnd.toDouble()).toInt()

                        for (i in 0 until dx) {
                            x--
                            err += 2 * dy
                            if (err > 0) {
                                y++
                                err -= 2 * dx
                            }
                            image.setRGB(x, y, curentColor.rgb)
                        }
                    }

                    (xEnd - xStart) <= 0 && (yEnd - yStart) <= 0 -> {
                        var x = max(xStart.toDouble(), xEnd.toDouble()).toInt()
                        var y = max(yStart.toDouble(), yEnd.toDouble()).toInt()

                        for (i in 0 until dx) {
                            x--
                            err += 2 * dy
                            if (err > 0) {
                                y--
                                err -= 2 * dx
                            }
                            image.setRGB(x, y, curentColor.rgb)
                        }
                    }
                }
            } else {
                // Второй случай: угол больше 45 градусов
                when {
                    (xEnd - xStart) >= 0 && (yEnd - yStart) >= 0 -> {
                        var x = min(xStart.toDouble(), xEnd.toDouble()).toInt()
                        var y = min(yStart.toDouble(), yEnd.toDouble()).toInt()

                        (0 until dy).forEach { i ->
                            y++
                            err += 2 * dx
                            if (err > 0) {
                                x++
                                err -= 2 * dy
                            }
                            image.setRGB(x, y, curentColor.rgb)
                        }
                    }

                    (xEnd - xStart) >= 0 && (yEnd - yStart) <= 0 -> {
                        var x = min(xStart.toDouble(), xEnd.toDouble()).toInt()
                        var y = max(yStart.toDouble(), yEnd.toDouble()).toInt()

                        (0 until dy).forEach { i ->
                            y--
                            err += 2 * dx
                            if (err > 0) {
                                x++
                                err -= 2 * dy
                            }
                            image.setRGB(x, y, curentColor.rgb)
                        }
                    }

                    (xEnd - xStart) <= 0 && (yEnd - yStart) >= 0 -> {
                        var x = max(xStart.toDouble(), xEnd.toDouble()).toInt()
                        var y = min(yStart.toDouble(), yEnd.toDouble()).toInt()

                        (0 until dy).forEach { i ->
                            y++
                            err += 2 * dx
                            if (err > 0) {
                                x--
                                err -= 2 * dy
                            }
                            image.setRGB(x, y, curentColor.rgb)
                        }
                    }

                    (xEnd - xStart) <= 0 && (yEnd - yStart) <= 0 -> {
                        var x = max(xStart.toDouble(), xEnd.toDouble()).toInt()
                        var y = max(yStart.toDouble(), yEnd.toDouble()).toInt()

                        (0 until dy).forEach { i ->
                            y--
                            err += 2 * dx
                            if (err > 0) {
                                x--
                                err -= 2 * dy
                            }
                            image.setRGB(x, y, curentColor.rgb)
                        }
                    }
                }
            }
        }
    }
}
