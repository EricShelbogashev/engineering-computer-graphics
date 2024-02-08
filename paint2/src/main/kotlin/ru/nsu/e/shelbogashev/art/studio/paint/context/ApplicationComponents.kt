package ru.nsu.e.shelbogashev.art.studio.paint.context

import ru.nsu.e.shelbogashev.art.studio.paint.model.DrawField
import javax.swing.JMenuBar
import javax.swing.JScrollPane
import javax.swing.JToolBar

data class ApplicationComponents(
    val toolbar: JToolBar,
    val menubar: JMenuBar,
    val scrollPane: JScrollPane,
    val field: DrawField
)