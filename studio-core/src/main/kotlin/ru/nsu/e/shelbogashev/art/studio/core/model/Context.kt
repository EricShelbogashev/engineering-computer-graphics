package ru.nsu.e.shelbogashev.art.studio.core.model

import ru.nsu.e.shelbogashev.art.studio.core.AppConfig

data class Context(
    val orchestrator: Orchestrator,
    val config: AppConfig
)