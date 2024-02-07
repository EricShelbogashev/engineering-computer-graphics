package ru.nsu.e.shelbogashev.art.studio.paint.initializer

import javax.swing.JComponent

object ComponentRegistry {
    private val components = mutableMapOf<String, JComponent>()

    fun register(component: JComponent) {
        require(!component.name.isNullOrBlank())
        components[component.name] = component
    }

    fun <T : JComponent> findByName(name: String): T? = components[name] as? T
}