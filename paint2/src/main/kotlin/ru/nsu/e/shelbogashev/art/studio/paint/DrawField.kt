package ru.nsu.e.shelbogashev.art.studio.paint

import mu.KotlinLogging
import ru.nsu.e.shelbogashev.art.studio.paint.tools.FillTool
import ru.nsu.e.shelbogashev.art.studio.paint.tools.LineTool
import ru.nsu.e.shelbogashev.art.studio.paint.tools.RegularTool
import ru.nsu.e.shelbogashev.art.studio.paint.tools.StarTool
import java.awt.*
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.awt.event.MouseMotionListener
import java.awt.image.BufferedImage
import javax.swing.JPanel
import javax.swing.SwingUtilities

/**
 * Панель для рисования и обработки событий мыши.
 */
class DrawField : JPanel(), MouseListener, MouseMotionListener {
    private val logger = KotlinLogging.logger("DrawField")
    private var minWidth = DEFAULT_MIN_WIDTH
    private var minHeight = DEFAULT_MIN_HEIGHT
    private var thickness = DEFAULT_THICKNESS

    private var image: BufferedImage? = null
    private var g2d: Graphics2D? = null
    private var curPenStyle = PenStyle.PEN
    private var currentColor: Color = Color.BLACK

    private val lineTool = LineTool()
    private val fillTool = FillTool()
    private var starTool = StarTool()
    private var regularTool = RegularTool()

    private val startPoint = Point(-1, -1)
    private var prevPoint = Point(-1, -1)
    private val saves = Story()

    /**
     * Инициализация компонента.
     */
    init {
        initializeImage()
        addMouseListener(this)
        addMouseMotionListener(this)
        saves.saveImage(image!!.data)
    }

    /**
     * Инициализация изображения.
     */
    private fun initializeImage() {
        image = BufferedImage(minWidth, minHeight, BufferedImage.TYPE_INT_RGB)
        g2d = image!!.createGraphics()
        setWhite()
    }

    /**
     * Изменение размера изображения.
     *
     * @param width Новая ширина.
     * @param height Новая высота.
     */
    fun resizeImage(width: Int, height: Int) {
        this.minWidth = width
        this.minHeight = height
        this.preferredSize = Dimension(width, height)

        val newImage = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
        this.g2d = newImage.createGraphics()
        setWhite()
        newImage.data = image!!.data
        this.image = newImage

        repaint()
    }

    /**
     * Установка цвета фона белым.
     */
    fun setWhite() {
        g2d!!.color = Color.WHITE
        g2d!!.background = Color.WHITE
        // TODO: полностью очистить весь экран
        g2d!!.fillRect(0, 0, minWidth, minHeight)
        repaint()
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        g.drawImage(image, 0, 0, this)
    }

    override fun mouseClicked(e: MouseEvent) {
        logger.trace { "click action : id=${e.id}, x=${e.x}, y=${e.y}" }
    }

    override fun mousePressed(e: MouseEvent) {
        if (e.x > minWidth || e.y > minHeight) return

        when (curPenStyle) {
            PenStyle.LINE -> {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    if ((startPoint.x == -1) and (startPoint.y == -1)) {
                        startPoint.x = e.x
                        startPoint.y = e.y
                    } else {
                        lineTool.drawLine(image!!, thickness, currentColor, startPoint, Point(e.x, e.y))
                        resetStartPos()
                    }
                }
            }

            PenStyle.STAR -> starTool.draw(image!!, e.point, currentColor)
            PenStyle.POLYGON -> regularTool.draw(image!!, e.point, currentColor)
            PenStyle.FILL -> fillTool.fill(image!!, e.point, currentColor)
            PenStyle.PEN -> {
                prevPoint = e.point
                g2d!!.color = currentColor
                g2d!!.fillOval(e.x - thickness / 2, e.y - thickness / 2, thickness, thickness)
            }

            else -> {}
        }
        repaint()
    }

    override fun mouseDragged(e: MouseEvent) {
        if (e.x > minWidth || e.y > minHeight) return

        if (curPenStyle == PenStyle.PEN) {
            g2d!!.color = currentColor
            g2d!!.fillOval(e.x - thickness / 2, e.y - thickness / 2, thickness, thickness)
            g2d!!.stroke = BasicStroke(thickness.toFloat(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND)
            g2d!!.drawLine(prevPoint.x, prevPoint.y, e.x, e.y)
            prevPoint = e.point
            repaint()
        } else if (curPenStyle == PenStyle.ERASER) {
            g2d!!.color = Color.WHITE
            g2d!!.fillOval(e.x, e.y, thickness, thickness)
            repaint()
        }
    }

    private fun resetStartPos() {
        startPoint.x = -1
        startPoint.y = -1
    }

    override fun mouseReleased(e: MouseEvent) {
        if (e.x > minWidth || e.y > minHeight) return

        saves.saveImage(image!!.data)
    }

    /**
     * Отмена последнего действия.
     */
    fun back() {
        val lastSave = saves.lastSave
        if (lastSave != null) {
            setWhite()
            image!!.data = lastSave
            repaint()
        }
    }

    override fun mouseEntered(e: MouseEvent) {
    }

    override fun mouseExited(e: MouseEvent) {
    }

    override fun mouseMoved(e: MouseEvent) {
    }

    /**
     * Установка толщины линии.
     *
     * @param thickness Толщина линии.
     */
    fun setThickness(thickness: Int) {
        this.thickness = thickness
    }

    /**
     * Получение изображения.
     *
     * @return Изображение.
     */
    fun getImage(): BufferedImage? {
        return image
    }

    /**
     * Установка изображения.
     *
     * @param newImage Новое изображение.
     */
    fun setImage(newImage: BufferedImage) {
        this.image = newImage
        g2d = newImage.createGraphics()
        repaint()
        println("New image has been set!")
    }

    /**
     * Установка стиля рисования.
     *
     * @param style Стиль рисования.
     */
    fun setPenStyle(style: PenStyle) {
        this.curPenStyle = style
        println("Pen changed to " + style.name)
    }

    /**
     * Установка цвета.
     *
     * @param color Новый цвет.
     */
    fun setColor(color: Color) {
        currentColor = color
    }

    /**
     * Установка параметров многоугольника и звезды.
     *
     * @param angle Угол.
     * @param numOfVertices Количество вершин.
     * @param bRadius Больший радиус.
     * @param sRadius Меньший радиус.
     */
    fun setRegularParameters(angle: Int, numOfVertices: Int, bRadius: Int, sRadius: Int) {
        starTool = StarTool(bRadius, sRadius, angle, numOfVertices)
        regularTool = RegularTool(bRadius, angle, numOfVertices)
    }

    /**
     * Перечисление стилей рисования.
     */
    enum class PenStyle {
        PEN,
        LINE,
        STAR,
        POLYGON,
        ERASER,
        FILL
    }

    companion object {
        private const val DEFAULT_MIN_WIDTH = 640
        private const val DEFAULT_MIN_HEIGHT = 480
        private const val DEFAULT_THICKNESS = 5
    }
}
