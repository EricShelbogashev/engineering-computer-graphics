package ru.nsu.e.shelbogashev.wireframe.bsplineeditor
import ru.nsu.e.shelbogashev.wireframe.wireframe.WireframeFrame
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.event.ActionEvent
import javax.swing.*

class BSplineEditor : JFrame() {

    init {
        defaultCloseOperation = DO_NOTHING_ON_CLOSE
        minimumSize = Dimension(900, 700)
        setBounds(380, 100, 900, 600)
        setupComponents()
        setupMenu()
        isVisible = true
    }

    private fun setupComponents() {
        val bSpline = BSpline()
        val editorPanel = SplinePanel(bSpline)
        val wireframeFrame = WireframeFrame(bSpline)
        val optionsPanel = OptionsPanel(editorPanel, wireframeFrame)

        val mainPanel = JPanel(BorderLayout())
        mainPanel.add(editorPanel, BorderLayout.CENTER)
        mainPanel.add(optionsPanel, BorderLayout.SOUTH)

        add(mainPanel)
    }

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
