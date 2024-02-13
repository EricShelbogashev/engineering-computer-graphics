package ru.nsu.e.shelbogashev.wireframe.wireframe

import ru.nsu.e.shelbogashev.wireframe.bsplineeditor.BSpline
import java.awt.*
import java.awt.event.*
import javax.swing.JPanel

/**
 * Представляет панель для отображения проволочной модели.
 */
class WireframePanel(private val bSpline: BSpline) : JPanel(), MouseListener, MouseMotionListener, MouseWheelListener {
    private val wireframe = Wireframe()

    private var prevPoint: Point? = null

    /**
     * Создает новую панель для отображения проволочной модели на основе заданной кривой Безье.
     */
    init {
        background = Color.WHITE

        addMouseListener(this)
        addMouseMotionListener(this)
        addMouseWheelListener(this)
    }

    public override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        val g2d = g as Graphics2D
        g2d.stroke = BasicStroke(2f)

        val centerX = width / 2
        val centerY = height / 2

        if (bSpline.getAnchorPoints().size < 4 || bSpline.splinePoints == null) {
            val cube = wireframe.cube
            val cubePoints = cube[0]!!
            val cubeEdges = cube[1]!!

            for (e in cubeEdges) {
                g2d.drawLine(
                    (centerX + cubePoints[e.x.toInt()].x * 200).toInt(),
                    (centerY - cubePoints[e.x.toInt()].y * 200).toInt(),
                    (centerX + cubePoints[e.y.toInt()].x * 200).toInt(),
                    (centerY - cubePoints[e.y.toInt()].y * 200).toInt()
                )
            }
        } else {
            wireframe.createWireframePoints(bSpline.splinePoints!!)
            val points = wireframe.wireframePoints
            val edges = wireframe.edges

            g2d.color = Color.BLACK
            var i = 0
            while (i < edges!!.size) {
                val p1 = points!![edges[i]]
                val p2 = points[edges[i + 1]]

                g2d.drawLine(
                    (centerX + p1.x * 200).toInt(), (centerY - p1.y * 200).toInt(),
                    (centerX + p2.x * 200).toInt(), (centerY - p2.y * 200).toInt()
                )
                i += 2
            }
        }
    }

    override fun mouseClicked(e: MouseEvent) {
    }

    override fun mousePressed(e: MouseEvent) {
        prevPoint = e.point
    }

    override fun mouseReleased(e: MouseEvent) {
    }

    override fun mouseEntered(e: MouseEvent) {
    }

    override fun mouseExited(e: MouseEvent) {
    }

    override fun mouseDragged(e: MouseEvent) {
        prevPoint?.let { wireframe.setRotationMatrix(it, e.point) }
        prevPoint = e.point

        repaint()
    }

    override fun mouseMoved(e: MouseEvent) {
    }

    override fun mouseWheelMoved(e: MouseWheelEvent) {
        repaint()
    }

    /**
     * Создает проволочную модель и отображает ее на панели.
     */
    fun createWireframe() {
        repaint()
    }
}
