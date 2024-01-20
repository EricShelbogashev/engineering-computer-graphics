package ru.nsu.e.shelbogashev.art.studio.core

import ru.nsu.e.shelbogashev.art.studio.core.model.screens.Screen
import ru.nsu.e.shelbogashev.art.studio.core.model.screens.ScreenAction
import java.io.Closeable

class StudioApp private constructor(
    private val splashScreen: Screen,
    private val onScreenStateChanged: (screen: Screen, action: ScreenAction) -> Unit
) : Runnable, Closeable {
    private val screens = mutableListOf(splashScreen)

    data class Builder(
        private var splashScreen: Screen? = null,
        private var onScreenStateChanged: ((screen: Screen, action: ScreenAction) -> Unit)? = null
    ) {
        fun splash(screen: Screen) = apply { this.splashScreen = screen }
        fun onScreenStateChanged(listener: (screen: Screen, action: ScreenAction) -> Unit) =
            apply { this.onScreenStateChanged = listener }

        fun build(): StudioApp {
            require(splashScreen != null) { "экран заставки не может быть null" }
            require(onScreenStateChanged != null) { "слушатель изменений состояния экрана не может быть null" }
            return StudioApp(splashScreen!!, onScreenStateChanged!!)
        }
    }

    override fun run() {
        onScreenStateChanged.invoke(splashScreen, ScreenAction.OPEN)
    }

    override fun close() {
        screens.forEach {
            onScreenStateChanged.invoke(it, ScreenAction.CLOSE)
        }
    }
}
