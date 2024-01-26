package ru.nsu.e.shelbogashev.art.studio.paint.frames

import java.awt.Dimension
import javax.swing.*
import javax.swing.event.ChangeEvent

/**
 * Класс OptionsPanel представляет собой JPanel, содержащий различные параметры для приложения рисования.
 */
class OptionsPanel : JPanel() {
    private val penSlider: JSlider
    private val penSizeSpinner: JSpinner

    private val vertexSlider: JSlider
    private val vertexSpinner: JSpinner

    private val angleSlider: JSlider
    private val angleSpinner: JSpinner

    private val bigRadiusSlider: JSlider
    private val bigRadiusSpinner: JSpinner

    private val smallRadiusSlider: JSlider
    private val smallRadiusSpinner: JSpinner

    /**
     * Инициализирует OptionsPanel, настраивая компоненты пользовательского интерфейса и их взаимодействия.
     */
    init {
        preferredSize = Dimension(330, 400)

        /*ОПЦИИ КАРАНДАША*/
        val penSize = JLabel("Размер карандаша")
        add(penSize)

        penSlider = JSlider(1, 20)
        penSlider.maximum = 20
        penSlider.minimum = 1
        penSlider.majorTickSpacing = 1
        penSlider.paintTicks = true
        penSlider.value = 5
        add(penSlider)

        val penSizeSpinnerModel = SpinnerNumberModel(5, 1, 20, 1)
        penSizeSpinner = JSpinner(penSizeSpinnerModel)
        add(penSizeSpinner)

        penSlider.addChangeListener { e: ChangeEvent? ->
            penSizeSpinner.value = penSlider.value
        }

        penSizeSpinner.addChangeListener { e: ChangeEvent? ->
            if (penSizeSpinner.value as Int > 20) penSizeSpinner.value = 20
            penSlider.value = (penSizeSpinner.value as Int)
        }

        /*-----------------*/

        /*ОПЦИИ МНОГОУГОЛЬНИКА*/
        val polygonOpt = JLabel("Вершины")
        add(polygonOpt)

        vertexSlider = JSlider(3, 16)
        vertexSlider.majorTickSpacing = 1
        vertexSlider.paintTicks = true
        vertexSlider.value = 5
        add(vertexSlider)

        val anglesSpinnerModel = SpinnerNumberModel(5, 3, 16, 1)
        vertexSpinner = JSpinner(anglesSpinnerModel)
        add(vertexSpinner)

        vertexSlider.addChangeListener { e: ChangeEvent? ->
            vertexSpinner.value = vertexSlider.value
        }

        vertexSpinner.addChangeListener { e: ChangeEvent? ->
            if (vertexSpinner.value as Int > 16) vertexSpinner.value = 16
            vertexSlider.value = (vertexSpinner.value as Int)
        }

        val angle = JLabel("Поворот")
        add(angle)

        angleSlider = JSlider(0, 360)
        angleSlider.majorTickSpacing = 1
        angleSlider.value = 0
        add(angleSlider)

        val angleModel = SpinnerNumberModel(0, 0, 360, 1)
        angleSpinner = JSpinner(angleModel)
        add(angleSpinner)

        angleSlider.addChangeListener { e: ChangeEvent? ->
            angleSpinner.value = angleSlider.value
        }

        angleSpinner.addChangeListener { e: ChangeEvent? ->
            if (angleSpinner.value as Int > 360) angleSpinner.value = 360
            angleSlider.value = (angleSpinner.value as Int)
        }

        val radius = JLabel("Радиус1")
        add(radius)

        bigRadiusSlider = JSlider(0, 100)
        bigRadiusSlider.majorTickSpacing = 1
        bigRadiusSlider.value = 50
        add(bigRadiusSlider)

        val radiusModel = SpinnerNumberModel(50, 0, 100, 1)
        bigRadiusSpinner = JSpinner(radiusModel)
        add(bigRadiusSpinner)

        bigRadiusSlider.addChangeListener { e: ChangeEvent? ->
            bigRadiusSpinner.value = bigRadiusSlider.value
        }

        bigRadiusSpinner.addChangeListener { e: ChangeEvent? ->
            if (bigRadiusSpinner.value as Int > 1000) bigRadiusSpinner.value = 100
            bigRadiusSlider.value = (bigRadiusSpinner.value as Int)
        }

        val smallRadius = JLabel("Радиус2")
        add(smallRadius)

        smallRadiusSlider = JSlider(0, 100)
        smallRadiusSlider.majorTickSpacing = 1
        smallRadiusSlider.value = 20
        add(smallRadiusSlider)

        val smallRadiusModel = SpinnerNumberModel(20, 1, 100, 1)
        smallRadiusSpinner = JSpinner(smallRadiusModel)
        add(smallRadiusSpinner)

        smallRadiusSlider.addChangeListener { e: ChangeEvent? ->
            smallRadiusSpinner.value = smallRadiusSlider.value
        }

        smallRadiusSpinner.addChangeListener { e: ChangeEvent? ->
            if (smallRadiusSpinner.value as Int > 100) smallRadiusSpinner.value = 100
            smallRadiusSlider.value = (smallRadiusSpinner.value as Int)
        }

        /*---------------*/
    }

    /**
     * Возвращает размер карандаша.
     *
     * @return Размер карандаша.
     */
    val penSize: Int
        get() = penSlider.value

    /**
     * Возвращает количество вершин многоугольника.
     *
     * @return Количество вершин многоугольника.
     */
    val numOfVertices: Int
        get() = vertexSlider.value

    /**
     * Возвращает угол поворота.
     *
     * @return Угол поворота.
     */
    val angle: Int
        get() = angleSlider.value

    /**
     * Возвращает радиус1.
     *
     * @return Радиус1.
     */
    val bigRadius: Int
        get() = bigRadiusSlider.value

    /**
     * Возвращает радиус2.
     *
     * @return Радиус2.
     */
    val smallRadius: Int
        get() = smallRadiusSlider.value
}
