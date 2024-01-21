package ru.nsu.e.shelbogashev.art.studio.core.model

import ru.nsu.e.shelbogashev.art.studio.core.model.screens.Screen

abstract class Orchestrator {

    /**
     * Запустить новый экран.
     */
    protected abstract fun open(screen: Screen)

    /**
     * Запросить закрытие экрана.
     */
    protected abstract fun close(screen: Screen)
}