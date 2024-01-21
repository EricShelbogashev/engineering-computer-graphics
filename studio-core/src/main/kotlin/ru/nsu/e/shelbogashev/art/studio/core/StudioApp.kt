package ru.nsu.e.shelbogashev.art.studio.core

import ru.nsu.e.shelbogashev.art.studio.core.model.Context
import ru.nsu.e.shelbogashev.art.studio.core.model.Orchestrator
import ru.nsu.e.shelbogashev.art.studio.core.model.screens.Screen
import ru.nsu.e.shelbogashev.art.studio.core.model.screens.ScreenAction

class StudioApp private constructor(
    private val splashProducer: (Context) -> Screen,
    private val onScreenStateChanged: (screen: Screen, action: ScreenAction) -> Unit
) : Orchestrator() {
    private val screens = mutableListOf<Screen>()

    data class Builder(
        private var splashProducer: ((Context) -> Screen)? = null,
        private var onScreenStateChanged: ((screen: Screen, action: ScreenAction) -> Unit)? = null
    ) {
        fun splash(splashProducer: (Context) -> Screen) = apply { this.splashProducer = splashProducer }
        fun onScreenStateChanged(listener: (screen: Screen, action: ScreenAction) -> Unit) =
            apply { this.onScreenStateChanged = listener }

        fun build(): StudioApp {
            require(splashProducer != null) { "поставщик экрана заставки не может быть null" }
            require(onScreenStateChanged != null) { "слушатель изменений состояния экрана не может быть null" }
            return StudioApp(splashProducer!!, onScreenStateChanged!!)
        }
    }

    fun run() {
        val splashScreen = splashProducer.invoke(buildContext())
        open(splashScreen)
    }

    private fun buildContext(): Context {
        val config = AppConfig()
        return Context(this, config)
    }

    fun shutdown() {
        screens.forEach {
            onScreenStateChanged.invoke(it, ScreenAction.CLOSE)
        }
    }

    override fun open(screen: Screen) {
        require(!screens.contains(screen)) {
            "недопустимо повторное открытие уже открытого экрана"
        }
        screens.add(screen)
        onScreenStateChanged.invoke(screen, ScreenAction.OPEN)
    }

    override fun close(screen: Screen) {
        require(screens.contains(screen)) {
            "недопустимо закрытие уже закрытого или не существующего экрана"
        }
        screens.remove(screen)
        onScreenStateChanged.invoke(screen, ScreenAction.CLOSE)
        screen.close()
    }
}
