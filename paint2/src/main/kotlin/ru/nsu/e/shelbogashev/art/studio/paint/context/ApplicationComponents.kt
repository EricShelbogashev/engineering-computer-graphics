package ru.nsu.e.shelbogashev.art.studio.paint.context

import ru.nsu.e.shelbogashev.art.studio.paint.etc.DrawField
import javax.swing.JMenuBar
import javax.swing.JToolBar

data class ApplicationComponents(
    val toolbar: JToolBar,
    val menubar: JMenuBar,
    val field: DrawField
)