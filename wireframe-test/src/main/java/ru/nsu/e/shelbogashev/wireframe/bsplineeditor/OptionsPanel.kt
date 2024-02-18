package ru.nsu.e.shelbogashev.wireframe.bsplineeditor

import ru.nsu.e.shelbogashev.wireframe.utils.Settings
import ru.nsu.e.shelbogashev.wireframe.wireframe.WireframeFrame
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.FlowLayout
import java.awt.event.ActionEvent
import javax.swing.*
import javax.swing.event.ChangeEvent
import javax.swing.event.ChangeListener

/**
 * Панель опций предоставляет пользователю интерфейс для настройки параметров приложения.
 */
class OptionsPanel(splinePanel: SplinePanel, private val wireframeFrame: WireframeFrame) : JPanel() {
    /**
     * Создает панель опций с заданными компонентами.
     */
    init {
        setupPanel()
        addApplyButton()
        addSpinnerWithLabel("Генератрисы", Settings.generatrixNum, 2, 1000, 1) { e: ChangeEvent ->
            Settings.generatrixNum = (e.source as JSpinner).value as Int
        }
        addSpinnerWithLabel("Круги", Settings.circlesNum, 2, 1000, 1) { e: ChangeEvent ->
            Settings.circlesNum = (e.source as JSpinner).value as Int

        }
        addSpinnerWithLabel("Точность", Settings.circlesAccuracy, 1, 1000, 1) { e: ChangeEvent ->
            Settings.circlesAccuracy = (e.source as JSpinner).value as Int

        }
        addSpinnerWithLabel("Сегменты", Settings.segmentsNum, 1, 1000, 1) { e: ChangeEvent ->
            Settings.segmentsNum = (e.source as JSpinner).value as Int
            splinePanel.recreateSpline()
        }
    }

    /**
     * Настройка внешнего вида панели опций.
     */
    private fun setupPanel() {
        layout = FlowLayout(FlowLayout.LEFT)
        preferredSize = Dimension(640, 200)
    }

    /**
     * Добавляет кнопку применения изменений.
     */
    private fun addApplyButton() {
        val applyButton = JButton("Применить")
        applyButton.addActionListener { _: ActionEvent? -> wireframeFrame.createWireframe() }
        add(applyButton, BorderLayout.SOUTH)
    }

    /**
     * Добавляет спиннер со значением и меткой.
     *
     * @param labelText      Текст метки.
     * @param initialValue   Начальное значение спиннера.
     * @param min            Минимальное значение спиннера.
     * @param max            Максимальное значение спиннера.
     * @param step           Шаг изменения значения спиннера.
     * @param changeListener Обработчик изменения значения спиннера.
     */
    private fun addSpinnerWithLabel(
        labelText: String, initialValue: Int, min: Int, max: Int, step: Int,
        changeListener: ChangeListener
    ) {
        val label = JLabel(labelText)
        add(label)

        val spinnerModel = SpinnerNumberModel(initialValue, min, max, step)
        val spinner = JSpinner(spinnerModel)
        spinner.addChangeListener(changeListener)
        add(spinner)
    }
}