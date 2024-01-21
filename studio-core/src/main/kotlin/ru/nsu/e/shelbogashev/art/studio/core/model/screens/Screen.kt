package ru.nsu.e.shelbogashev.art.studio.core.model.screens

import ru.nsu.e.shelbogashev.art.studio.core.model.Context
import java.io.Closeable

abstract class Screen(protected val context: Context) : Closeable {
    abstract fun controller(): ScreenController
}