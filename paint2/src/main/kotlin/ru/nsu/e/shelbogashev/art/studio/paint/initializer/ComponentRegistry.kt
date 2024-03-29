@file:Suppress("UNCHECKED_CAST")

package ru.nsu.e.shelbogashev.art.studio.paint.initializer

import javax.swing.JComponent

object ComponentRegistry {
    private val components = mutableMapOf<String, JComponent>()

    fun register(component: JComponent) {
        require(!component.name.isNullOrBlank())
        components[component.name] = component
    }

    fun <T : JComponent> findById(name: String): T {
        val jComponent: JComponent = components[name]
            ?: throw NoSuchElementException("компонент с идентификатором $name не найден")
        return jComponent as T
    }

    fun all(): Collection<JComponent> = components.values.toList()
}