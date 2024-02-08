package ru.nsu.e.shelbogashev.art.studio.paint.model.menu

import ru.nsu.e.shelbogashev.art.studio.paint.context.ApplicationContext
import ru.nsu.e.shelbogashev.art.studio.paint.model.support.StringResource
import javax.swing.JDialog
import javax.swing.JFrame
import javax.swing.JScrollPane
import javax.swing.JTextPane

class InstructionDialog(parentFrame: JFrame?, context: ApplicationContext) :
    JDialog(parentFrame, StringResource.loadString("dialogue_instruction_title", context.properties.locale), true) {
    init {
        val textPane = JTextPane()
        textPane.contentType = "text/html"
        textPane.isEditable = false

        val instructionsHtml = StringResource.loadString("dialogue_instruction_content", context.properties.locale)

        textPane.text = instructionsHtml
        val scrollPane = JScrollPane(textPane)

        contentPane.add(scrollPane)
        setSize(400, 300)
        setLocationRelativeTo(parentFrame)
    }
}
