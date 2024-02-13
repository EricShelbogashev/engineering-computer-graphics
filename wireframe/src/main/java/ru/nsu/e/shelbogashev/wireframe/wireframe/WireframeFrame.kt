package ru.nsu.e.shelbogashev.wireframe.wireframe

import ru.nsu.e.shelbogashev.wireframe.bsplineeditor.BSpline
import java.awt.Dimension
import javax.swing.JFrame

/**
 * Представляет собой графическое окно для отображения проволочной модели.
 */
class WireframeFrame(bSpline: BSpline?) : JFrame() {
    private val wireframePanel = WireframePanel(bSpline!!)

    /**
     * Создает новый экземпляр графического окна для проволочной модели.
     *
     * @param bSpline Кривая Безье, на основе которой будет создана проволочная модель.
     */
    init {
        val width = 900
        val height = 600
        minimumSize = Dimension(width, height)

        add(wireframePanel)

        isVisible = true
    }

    /**
     * Создает проволочную модель и отображает ее на панели.
     */
    fun createWireframe() {
        wireframePanel.createWireframe()
    }
}
