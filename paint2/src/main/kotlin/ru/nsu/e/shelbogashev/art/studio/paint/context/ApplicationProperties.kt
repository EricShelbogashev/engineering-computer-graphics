package ru.nsu.e.shelbogashev.art.studio.paint.context

import java.awt.Dimension
import java.util.*
import javax.swing.JFrame

data class ApplicationProperties(
    val locale: Locale = Locale.of("ru"),
    val windowTitle: String = "Paint",
    val windowSize: Dimension = Dimension(640, 480),
    val minimumWindowSize: Dimension = Dimension(640, 480),
    val defaultCloseOperation: Int = JFrame.EXIT_ON_CLOSE
)