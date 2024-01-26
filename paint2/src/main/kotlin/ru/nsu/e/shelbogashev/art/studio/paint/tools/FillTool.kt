package ru.nsu.e.shelbogashev.art.studio.paint.tools

import java.awt.Color
import java.awt.Graphics2D
import java.awt.Point
import java.awt.image.BufferedImage
import java.util.*

class FillTool {
    private val spanStack = Stack<Span>()
    private var image: BufferedImage? = null
    private var g2d: Graphics2D? = null
    private var colorToSet = 0
    private var oldColor = 0

    private var maxUpX = 0
    private var maxDownX = 0

    // Добавление нового отрезка в стек
    private fun addNewSpan(seed: Point) {
        val newSpanStart = findSpanStart(seed)
        val newSpanEnd = findSpanEnd(seed)

        spanStack.push(Span(newSpanStart, newSpanEnd))
    }

    // Поиск начала отрезка
    private fun findSpanStart(seed: Point): Point {
        val newSpanStart = Point(seed)
        while (newSpanStart.x >= 0 && image!!.getRGB(newSpanStart.x, newSpanStart.y) == oldColor) {
            newSpanStart.x--
        }
        newSpanStart.x++
        return newSpanStart
    }

    // Поиск конца отрезка
    private fun findSpanEnd(seed: Point): Point {
        val newSpanEnd = Point(seed)
        while (newSpanEnd.x < image!!.width && image!!.getRGB(newSpanEnd.x, newSpanEnd.y) == oldColor) {
            newSpanEnd.x++
        }
        newSpanEnd.x--
        return newSpanEnd
    }

    // Поиск нового отрезка по текущему пикселу
    private fun findNewSpan(point: Point) {
        if ((point.y - 1) >= 0 && (point.y + 1) < image!!.height) {
            if (point.x > maxUpX && image!!.getRGB(point.x, point.y + 1) == oldColor) {
                addNewSpan(Point(point.x, point.y + 1))
                maxUpX = spanStack.peek().spanEnd.x
            }
            if (point.x > maxDownX && image!!.getRGB(point.x, point.y - 1) == oldColor) {
                addNewSpan(Point(point.x, point.y - 1))
                maxDownX = spanStack.peek().spanEnd.x
            }
        }
    }

    // Запуск заливки
    private fun startFilling() {
        val curSpan = spanStack.pop()
        g2d!!.color = Color(colorToSet)
        g2d!!.drawLine(curSpan.spanStart.x, curSpan.spanStart.y, curSpan.spanEnd.x, curSpan.spanEnd.y)

        for (x in curSpan.spanStart.x until curSpan.spanEnd.x) {
            findNewSpan(Point(x, curSpan.spanStart.y))
        }

        maxUpX = 0
        maxDownX = 0
    }

    // Метод заливки
    fun fill(image: BufferedImage, seed: Point, newColor: Color) {
        this.image = image
        this.g2d = image.graphics as Graphics2D
        this.colorToSet = newColor.rgb
        this.oldColor = image.getRGB(seed.x, seed.y)

        if (oldColor != colorToSet) {
            addNewSpan(seed)
            while (!spanStack.isEmpty()) {
                startFilling()
            }
        }
    }

    // Внутренний класс для представления отрезка
    private class Span(val spanStart: Point, val spanEnd: Point)
}
