package ru.nsu.e.shelbogashev.wireframe.bsplineeditor

import ru.nsu.e.shelbogashev.wireframe.utils.Point2D
import ru.nsu.e.shelbogashev.wireframe.utils.Settings
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import java.awt.event.*
import javax.swing.JPanel
import javax.swing.JScrollPane

/**
 * Панель для редактирования B-сплайнов.
 */
class SplinePanel(spline: BSpline) : JPanel(), MouseMotionListener, MouseListener, MouseWheelListener {
    private val bSpline: BSpline
    private val scrollPane = JScrollPane()
    private var indent = Settings.DEFAULT_INDENT
    private var curX = 0
    private var curY = 0
    private var activePointIndex = 0
    private var curAction = Action.NOTHING
    private var width = 640
    private var height = 240

    /**
     * Создает панель для редактирования B-сплайнов с заданными параметрами.
     */
    init {
        preferredSize = Dimension(width, height)
        background = Color.BLACK
        bSpline = spline
        scrollPane.maximumSize = Dimension(width, height)
        scrollPane.setViewportView(this)
        scrollPane.revalidate()
        addMouseListener(this)
        addMouseMotionListener(this)
        addMouseWheelListener(this)
    }

    private fun drawAxis(g: Graphics) {
        g.color = Color.WHITE
        g.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2)
        g.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight())
        val height = getHeight()
        val width = getWidth()
        var linesNumber = (width / 2) / indent
        for (i in 1..linesNumber) {
            g.drawLine(width / 2 - indent * i, height / 2 - 3, width / 2 - indent * i, height / 2 + 3)
            g.drawLine(width / 2 + indent * i, height / 2 - 3, width / 2 + indent * i, height / 2 + 3)
        }
        linesNumber = (height / 2) / indent
        for (i in 1..linesNumber) {
            g.drawLine(width / 2 - 3, height / 2 - indent * i, width / 2 + 3, height / 2 - indent * i)
            g.drawLine(width / 2 - 3, height / 2 + indent * i, width / 2 + 3, height / 2 + indent * i)
        }
    }

    private fun drawSplineAnchorPoints(g: Graphics) {
        g.color = Color.GREEN
        val points = bSpline.getAnchorPoints()
        val centerX = getWidth() / 2
        val centerY = getHeight() / 2
        for (p in points) {
            g.drawOval(
                (centerX + p.x * indent - Settings.RADIUS / 2).toInt(),
                (centerY - p.y * indent - Settings.RADIUS / 2).toInt(),
                Settings.RADIUS,
                Settings.RADIUS
            )
        }
        g.color = Color.WHITE
        drawLinesBetweenPoints(g, points)
    }

    private fun drawLinesBetweenPoints(g: Graphics, points: List<Point2D>) {
        for (i in 0 until points.size - 1) {
            val p1 = convertToScreen(points[i])
            val p2 = convertToScreen(points[i + 1])
            g.drawLine(p1.x.toInt(), p1.y.toInt(), p2.x.toInt(), p2.y.toInt())
        }
    }

    private fun drawSpline(g: Graphics) {
        g.color = Color.RED
        val points = bSpline.splinePoints
        if (points == null || points.size < 4) return
        drawLinesBetweenPoints(g, points)
    }

    private fun convertToScreen(p: Point2D): Point2D {
        val centerX = getWidth() / 2
        val centerY = getHeight() / 2
        val x = centerX + p.x * indent
        val y = centerY - p.y * indent
        return Point2D(x, y)
    }

    /**
     * Пересоздает B-сплайн и перерисовывает панель.
     */
    fun recreateSpline() {
        bSpline.createBSpline()
        repaint()
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        drawAxis(g)
        drawSplineAnchorPoints(g)
        drawSpline(g)
    }

    override fun mouseDragged(e: MouseEvent) {
        val centerX = getWidth().toDouble() / 2
        val centerY = getHeight().toDouble() / 2
        when (curAction) {
            Action.POINT_MOVING -> {
                val curPoint = bSpline.getAnchorPoints()[activePointIndex]
                curPoint.x = (e.x - centerX) / indent
                curPoint.y = (centerY - e.y) / indent
                bSpline.createBSpline()
            }

            Action.CANVAS_MOVING -> {
                val dx = curX - e.x
                val dy = curY - e.y
                val panelSize = Dimension(getWidth(), getHeight())
                val viewPos = scrollPane.viewport.viewPosition
                val maxX = viewPos.x + scrollPane.viewport.width
                val maxY = viewPos.y + scrollPane.viewport.height
                if (dx < 0 && viewPos.x == 0) {
                    width -= dx
                    curX -= dx
                } else if (dx >= 0 && maxX == panelSize.width) {
                    width += dx
                }
                if (dy < 0 && viewPos.y == 0) {
                    height -= dy
                    curY -= dy
                } else if (dy >= 0 && maxY == panelSize.height) {
                    height += dy
                }
                scrollPane.horizontalScrollBar.value = viewPos.x + dx
                scrollPane.verticalScrollBar.value = viewPos.y + dy
            }

            Action.NOTHING -> {}
        }
        repaint()
    }

    override fun mouseMoved(e: MouseEvent) {
    }

    override fun mouseClicked(e: MouseEvent) {
        val centerX = getWidth().toDouble() / 2
        val centerY = getHeight().toDouble() / 2
        if (e.button == MouseEvent.BUTTON1) bSpline.addAnchorPoint(
            Point2D(
                (e.x - centerX) / indent, (centerY - e.y) / indent
            )
        )
        if (e.button == MouseEvent.BUTTON3) bSpline.deleteAnchorPoint()
        bSpline.createBSpline()
        repaint()
    }

    override fun mousePressed(e: MouseEvent) {
        curX = e.x
        curY = e.y
        checkPoint()
    }

    private fun checkPoint() {
        val anchorPoints = bSpline.getAnchorPoints()
        val centerX = getWidth() / 2
        val centerY = getHeight() / 2
        var i = 0
        for (p in anchorPoints) {
            val globalX = p.x * indent + centerX
            val globalY = centerY - p.y * indent
            if (curX <= globalX + Settings.RADIUS && curX >= globalX - Settings.RADIUS && curY <= globalY + Settings.RADIUS && curY >= globalY - Settings.RADIUS) {
                activePointIndex = i
                curAction = Action.POINT_MOVING
                break
            }
            i++
        }
    }

    override fun mouseReleased(e: MouseEvent) {
        curAction = Action.NOTHING
        repaint()
    }

    override fun mouseEntered(e: MouseEvent) {
    }

    override fun mouseExited(e: MouseEvent) {
    }

    override fun mouseWheelMoved(e: MouseWheelEvent) {
        val modifier = e.wheelRotation
        indent -= modifier
        repaint()
    }

    internal enum class Action {
        POINT_MOVING,
        CANVAS_MOVING,
        NOTHING
    }
}