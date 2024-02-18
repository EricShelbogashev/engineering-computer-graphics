package ru.nsu.e.shelbogashev.art.studio.paint.model.frames

import ru.nsu.e.shelbogashev.art.studio.paint.context.ApplicationContext
import ru.nsu.e.shelbogashev.art.studio.paint.model.support.StringResource
import java.awt.*
import javax.swing.*

internal class OptionsPanel(context: ApplicationContext) : JPanel() {
    private val penSlider: JSlider
    private val vertexSlider: JSlider
    private val angleSlider: JSlider
    private val bigRadiusSlider: JSlider
    private val smallRadiusSlider: JSlider
    private val penSizeSpinner: JSpinner
    private val vertexSpinner: JSpinner
    private val angleSpinner: JSpinner
    private val bigRadiusSpinner: JSpinner
    private val smallRadiusSpinner: JSpinner

    init {
        preferredSize = Dimension(600, 400)
        layout = GridBagLayout()
        val gbc = GridBagConstraints()

        gbc.fill = GridBagConstraints.HORIZONTAL
        gbc.insets = Insets(2, 2, 2, 2) // Optional: adjust spacing between components

        // Pen Size
        addComponent(JLabel(StringResource.loadString("dialogue_options_label_pen_size", context.properties.locale)), 0, 0, 1, gbc)
        penSlider = createSlider(1, 20, 5, 1)
        addComponent(penSlider, 0, 1, 1, gbc)
        penSizeSpinner = createSpinner(SpinnerNumberModel(5, 1, 20, 1), 0, 2, gbc)

        // Polygon vertices
        addComponent(JLabel(StringResource.loadString("dialogue_options_label_regular_vertices_number", context.properties.locale)), 1, 0, 1, gbc)
        vertexSlider = createSlider(3, 16, 5, 1)
        addComponent(vertexSlider, 1, 1, 1, gbc)
        vertexSpinner = createSpinner(SpinnerNumberModel(5, 3, 16, 1), 1, 2, gbc)

        // Angle
        addComponent(JLabel(StringResource.loadString("dialogue_options_label_rotation_angle", context.properties.locale)), 2, 0, 1, gbc)
        angleSlider = createSlider(0, 360, 0, 1)
        addComponent(angleSlider, 2, 1, 1, gbc)
        angleSpinner = createSpinner(SpinnerNumberModel(0, 0, 360, 1), 2, 2, gbc)

        // Big Radius
        addComponent(JLabel(StringResource.loadString("dialogue_options_label_outer_radius", context.properties.locale)), 3, 0, 1, gbc)
        bigRadiusSlider = createSlider(0, 100, 50, 1)
        addComponent(bigRadiusSlider, 3, 1, 1, gbc)
        bigRadiusSpinner = createSpinner(SpinnerNumberModel(50, 0, 100, 1), 3, 2, gbc)

        // Small Radius
        addComponent(JLabel(StringResource.loadString("dialogue_options_label_inner_radius", context.properties.locale)), 4, 0, 1, gbc)
        smallRadiusSlider = createSlider(0, 50, 20, 1)
        addComponent(smallRadiusSlider, 4, 1, 1, gbc)
        smallRadiusSpinner = createSpinner(SpinnerNumberModel(20, 0, 100, 1), 4, 2, gbc)

        // Setup change listeners
        setupChangeListeners()
    }

    private fun addComponent(component: Component, row: Int, col: Int, width: Int, gbc: GridBagConstraints) {
        gbc.gridx = col
        gbc.gridy = row
        gbc.gridwidth = width
        add(component, gbc)
    }

    private fun createSlider(min: Int, max: Int, value: Int, majorTickSpacing: Int): JSlider {
        val slider = JSlider(JSlider.HORIZONTAL, min, max, value)
        slider.majorTickSpacing = majorTickSpacing
        slider.paintTicks = true
        return slider
    }

    private fun createSpinner(model: SpinnerModel, row: Int, col: Int, gbc: GridBagConstraints): JSpinner {
        val spinner = JSpinner(model)
        addComponent(spinner, row, col, 1, gbc)
        return spinner
    }

    private fun setupChangeListener(slider: JSlider, spinner: JSpinner) {
        slider.addChangeListener {
            spinner.value = slider.value
        }
        spinner.addChangeListener {
            slider.value = spinner.value as Int
        }
    }

    private fun setupBigRadiusChangeListener(
        slider: JSlider,
        spinner: JSpinner,
        smallSpinner: JSpinner,
        smallSlider: JSlider
    ) {
        slider.addChangeListener {
            spinner.value = slider.value
            smallSlider.maximum = slider.value

            if (slider.value > smallSpinner.value as Int) {
                smallSpinner.value = smallSlider.value
            }
            smallSpinner.model = SpinnerNumberModel(smallSpinner.value as Int, 0, slider.value, 1)
        }
        spinner.addChangeListener {
            slider.value = spinner.value as Int
            smallSlider.maximum = slider.value

            if (slider.value > smallSpinner.value as Int) {
                smallSpinner.value = smallSlider.value
            }
            smallSpinner.model = SpinnerNumberModel(smallSpinner.value as Int, 0, slider.value, 1)
        }
    }

    private fun setupChangeListeners() {
        setupChangeListener(penSlider, penSizeSpinner)
        setupChangeListener(vertexSlider, vertexSpinner)
        setupChangeListener(angleSlider, angleSpinner)
        setupBigRadiusChangeListener(bigRadiusSlider, bigRadiusSpinner, smallRadiusSpinner, smallRadiusSlider)
        setupChangeListener(smallRadiusSlider, smallRadiusSpinner)
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