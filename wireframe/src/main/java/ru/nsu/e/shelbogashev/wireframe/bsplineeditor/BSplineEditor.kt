package ru.nsu.e.shelbogashev.wireframe.bsplineeditor

import ru.nsu.e.shelbogashev.wireframe.wireframe.WireframeFrame
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.event.ActionEvent
import javax.swing.*

/**
 * Класс BSplineEditor представляет графический интерфейс для редактирования B-сплайнов.
 */
class BSplineEditor : JFrame() {
    /**
     * Создает экземпляр класса BSplineEditor.
     */
    init {
        initializeFrame()
        setupComponents()
        setupMenu()
        isVisible = true
    }

    /**
     * Инициализирует основные параметры окна.
     */
    private fun initializeFrame() {
        defaultCloseOperation = DO_NOTHING_ON_CLOSE
        minimumSize = Dimension(900, 700)
        setBounds(380, 100, 900, 600)
    }

    /**
     * Настроивает компоненты интерфейса.
     */
    private fun setupComponents() {
        val bSpline = BSpline()
        val editorPanel = SplinePanel(bSpline)
        add(editorPanel, BorderLayout.CENTER)

        val wireframeFrame = WireframeFrame(bSpline)
        val optionsPanel = OptionsPanel(editorPanel, wireframeFrame)
        add(optionsPanel, BorderLayout.SOUTH)
    }

    /**
     * Настроивает меню приложения.
     */
    private fun setupMenu() {
        val menu = JMenuBar()
        add(menu, BorderLayout.NORTH)

        val file = JMenu("Файл")
        menu.add(file)

        val about = JMenu("О программе")
        menu.add(about)

        val save = JMenuItem("Сохранить")
        val load = JMenuItem("Загрузить")
        file.add(save)
        file.add(load)

        val aboutItem = JMenuItem("О программе")
        about.add(aboutItem)

        aboutItem.addActionListener { _: ActionEvent? -> showAboutDialog() }
    }

    /**
     * Отображает диалоговое окно с информацией о программе и авторе.
     */
    private fun showAboutDialog() {
        JOptionPane.showConfirmDialog(
            null,
            "Автор: Шелбогашев Эрик 21209, ФИТ НГУ",
            "О программе",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.PLAIN_MESSAGE
        )
    }
}
