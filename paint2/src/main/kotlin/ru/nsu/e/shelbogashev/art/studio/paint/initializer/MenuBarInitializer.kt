package ru.nsu.e.shelbogashev.art.studio.paint.initializer

import PaintApplication
import ru.nsu.e.shelbogashev.art.studio.paint.etc.support.StringResource
import java.awt.event.ActionEvent
import javax.swing.*

class MenuBarInitializer {

    data class MenuItemConfig(
        val id: String,
        val key: String,
        val action: ((ActionEvent) -> Unit)? = null,
        val isRadioButton: Boolean = false
    )

    private fun createMenuItem(config: MenuItemConfig, application: PaintApplication): JMenuItem {
        val text = StringResource.loadString(config.key, application.locale)
        val result = if (config.isRadioButton) {
            JRadioButtonMenuItem(text)
        } else {
            JMenuItem(text)
        }
        return result.apply {
            config.action?.let { addActionListener(it) }
            this.name = config.id
        }
    }

    private fun addMenuItems(
        menu: JMenu,
        items: List<MenuItemConfig>,
        application: PaintApplication,
        group: ButtonGroup? = null
    ): JMenu {
        items.forEach { config ->
            val item = createMenuItem(config, application)
            menu.add(item)
            group?.add(item)
            ComponentRegistry.register(item)
        }
        return menu
    }

    fun initMenuBar(application: PaintApplication): JMenuBar {
        val menuBar = JMenuBar()

        // File menu
        val fileMenuItems = listOf(
            MenuItemConfig("menu_file_button_save_file", "menu_file_button_save_file"),
            MenuItemConfig("menu_file_button_open_file", "menu_file_button_open_file"),
            MenuItemConfig("menu_file_button_exit", "menu_file_button_exit")
        )
        val fileMenu = addMenuItems(
            JMenu(StringResource.loadString("menu_file_label", application.locale)),
            fileMenuItems,
            application
        )
        menuBar.add(fileMenu)

        // View menu
        val viewMenuItems = listOf(
            MenuItemConfig("menu_tools_button_pen", "menu_tools_button_pen", isRadioButton = true),
            MenuItemConfig("menu_tools_button_line", "menu_tools_button_line", isRadioButton = true),
            MenuItemConfig("menu_tools_button_regular", "menu_tools_button_regular", isRadioButton = true),
            MenuItemConfig("menu_tools_button_star", "menu_tools_button_star", isRadioButton = true),
            MenuItemConfig("menu_tools_button_fill", "menu_tools_button_fill", isRadioButton = true),
            MenuItemConfig("menu_tools_button_eraser", "menu_tools_button_eraser", isRadioButton = true),
            MenuItemConfig("menu_tools_button_clear", "menu_tools_button_clear")
        )
        val toolsGroup = ButtonGroup()
        val viewMenu = addMenuItems(
            JMenu(StringResource.loadString("menu_tools_label", application.locale)),
            viewMenuItems,
            application,
            toolsGroup
        )
        viewMenu.add(
            createMenuItem(
                MenuItemConfig("menu_tools_button_resize", "menu_tools_button_resize"),
                application
            )
        )
        menuBar.add(viewMenu)

        // About menu
        val aboutMenuItems = listOf(
            MenuItemConfig("menu_about_button_about", "menu_about_button_about")
        )
        val aboutMenu = addMenuItems(
            JMenu(StringResource.loadString("menu_about_label", application.locale)),
            aboutMenuItems,
            application
        )
        menuBar.add(aboutMenu)

        // Resize dialogue
        val resizeDialogue = JPanel().apply {
            val widthModel = SpinnerNumberModel(640, 640, 2100, 1)
            val widthField = JSpinner(widthModel)
            val heightModel = SpinnerNumberModel(480, 480, 2000, 1)
            val heightField = JSpinner(heightModel)
            name = "dialogue_resize"
            add(JLabel(StringResource.loadString("dialogue_resize_button_width", application.locale)))
            add(widthField)
            add(JLabel(StringResource.loadString("dialogue_resize_button_height", application.locale)))
            add(heightField)
        }
        ComponentRegistry.register(resizeDialogue)

        return menuBar
    }
}
