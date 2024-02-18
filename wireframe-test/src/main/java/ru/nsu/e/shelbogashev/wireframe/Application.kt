package ru.nsu.e.shelbogashev.wireframe

import com.formdev.flatlaf.themes.FlatMacLightLaf
import ru.nsu.e.shelbogashev.wireframe.bsplineeditor.BSplineEditor

object Application {
    @JvmStatic
    fun main(args: Array<String>) {
        FlatMacLightLaf.setup()
        BSplineEditor()
    }
}
