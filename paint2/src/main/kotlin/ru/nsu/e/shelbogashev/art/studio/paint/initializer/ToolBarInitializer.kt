package ru.nsu.e.shelbogashev.art.studio.paint.initializer

import PaintApplication
import ru.nsu.e.shelbogashev.art.studio.paint.model.support.IconResource
import ru.nsu.e.shelbogashev.art.studio.paint.model.support.StringResource
import java.awt.Color
import java.awt.Dimension
import javax.swing.Icon
import javax.swing.JButton
import javax.swing.JToolBar

class ToolBarInitializer {
    data class ButtonConfig(
        val id: String,
        val icon: IconResource? = null,
        val tooltipKey: String,
        val background: Color? = null
    )

    private fun buildToolbarButton(
        id: String,
        icon: Icon? = null,
        toolTipText: String? = null,
        background: Color? = null
    ): JButton {
        val size = Dimension(28, 24)
        return JButton().apply {
            name = id
            isFocusPainted = false
            preferredSize = size
            maximumSize = size
            isOpaque = true
            isBorderPainted = false
            this.background = background
            this.toolTipText = toolTipText
            this.icon = icon
        }
    }

    fun initToolBar(application: PaintApplication): JToolBar {
        val toolBar = JToolBar().apply {
            isFloatable = false
            isRollover = true
            isVisible = true
        }

        val buttonConfigs = listOf(
            ButtonConfig("toolbar_button_pen", IconResource.PEN, "toolbar_button_hint_pen"),
            ButtonConfig("toolbar_button_options", IconResource.OPTIONS, "toolbar_button_hint_options"),
            ButtonConfig("toolbar_button_clear_all", IconResource.CLEAN, "toolbar_button_hint_clear_all"),
            ButtonConfig("toolbar_button_eraser", IconResource.ERASER, "toolbar_button_hint_eraser"),
            ButtonConfig("toolbar_button_line", IconResource.LINE, "toolbar_button_hint_line"),
            ButtonConfig("toolbar_button_star", IconResource.STAR, "toolbar_button_hint_star"),
            ButtonConfig("toolbar_button_fill", IconResource.FILL, "toolbar_button_hint_fill"),
            ButtonConfig("toolbar_button_palette", IconResource.PALETTE, "toolbar_button_hint_palette"),
            ButtonConfig("toolbar_button_regular", IconResource.REGULAR, "toolbar_button_hint_regular"),
            ButtonConfig("toolbar_button_undo", IconResource.BACK, "toolbar_button_hint_undo"),
            ButtonConfig("toolbar_button_red", null, "toolbar_button_hint_red", Color.RED),
            ButtonConfig("toolbar_button_green", null, "toolbar_button_hint_green", Color.GREEN),
            ButtonConfig("toolbar_button_blue", null, "toolbar_button_hint_blue", Color.BLUE),
            ButtonConfig("toolbar_button_black", null, "toolbar_button_hint_black", Color.BLACK)
        )

        buttonConfigs.forEach { config ->
            val icon = config.icon?.loadIcon()
            val tooltip = StringResource.loadString(config.tooltipKey, application.locale)
            val button = buildToolbarButton(config.id, icon, tooltip, config.background)
            toolBar.add(button)
            ComponentRegistry.register(button)
        }

        return toolBar
    }
}