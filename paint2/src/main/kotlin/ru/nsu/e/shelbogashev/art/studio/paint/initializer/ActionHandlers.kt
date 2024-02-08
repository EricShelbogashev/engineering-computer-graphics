package ru.nsu.e.shelbogashev.art.studio.paint.initializer

import PaintApplication
import ru.nsu.e.shelbogashev.art.studio.paint.context.ApplicationContext
import ru.nsu.e.shelbogashev.art.studio.paint.model.DrawField
import ru.nsu.e.shelbogashev.art.studio.paint.model.frames.LoadFrame
import ru.nsu.e.shelbogashev.art.studio.paint.model.frames.OptionsPanel
import ru.nsu.e.shelbogashev.art.studio.paint.model.frames.SaveFrame
import ru.nsu.e.shelbogashev.art.studio.paint.model.menu.InstructionDialog
import ru.nsu.e.shelbogashev.art.studio.paint.model.support.StringResource
import java.awt.Color
import java.awt.Component
import java.awt.event.ActionEvent
import java.io.IOException
import javax.swing.*
import kotlin.system.exitProcess

class ActionHandlers {
    fun setupActionListeners(application: PaintApplication, context: ApplicationContext) {
        val toolbarItems: Collection<Component> = ComponentRegistry.all()
            .filter { component ->
                component.name.startsWith("toolbar_button") || component.name.startsWith("menu_tools_button")
            }

        val exitItem = ComponentRegistry.findById<JMenuItem>("menu_file_button_exit")
        exitItem.addActionListener { exitProcess(0) }

        val saveItem = ComponentRegistry.findById<JMenuItem>("menu_file_button_save_file")
        val saveFrame = SaveFrame(context.components.field)
        saveItem.addActionListener { e: ActionEvent? ->
            try {
                saveFrame.saveImage()
            } catch (ex: IOException) {
                throw RuntimeException(ex)
            }
        }

        val loadItem = ComponentRegistry.findById<JMenuItem>("menu_file_button_open_file")
        val loadFrame = LoadFrame(context.components.field)
        loadItem.addActionListener { e: ActionEvent? ->
            try {
                loadFrame.loadImage()
            } catch (ex: IOException) {
                throw RuntimeException(ex)
            }
        }

        /*---------------------*/


        fun updateSelected(tool: Component) {
            toolbarItems.forEach {
                when (it) {
                    is JButton -> it.isSelected = false
                    is JMenuItem -> it.isSelected = false
                }
            }
            val toolName = when (tool) {
                is JButton -> tool.name.removePrefix("toolbar_button_")
                is JMenuItem -> tool.name.removePrefix("menu_tools_button_")
                else -> throw IllegalStateException("недопустимый объект в панели инструментах или меню")
            }
            toolbarItems.filter { it.name.endsWith(toolName) }.forEach {
                when (it) {
                    is JButton -> it.isSelected = true
                    is JMenuItem -> it.isSelected = true
                }
            }
        }

        fun baseToolActionListener(event: ActionEvent) {
            val actionButton = event.source as JComponent
            context.components.field.setPenStyle(
                when (actionButton.name) {
                    "toolbar_button_pen", "menu_tools_button_pen" -> DrawField.PenStyle.PEN
                    "toolbar_button_star", "menu_tools_button_star" -> DrawField.PenStyle.STAR
                    "toolbar_button_regular", "menu_tools_button_regular" -> DrawField.PenStyle.REGULAR
                    "toolbar_button_line", "menu_tools_button_line" -> DrawField.PenStyle.LINE
                    "toolbar_button_eraser", "menu_tools_button_eraser" -> DrawField.PenStyle.ERASER
                    "toolbar_button_fill", "menu_tools_button_fill" -> DrawField.PenStyle.FILL
                    else -> throw IllegalStateException("действие для выбранного инструмента не определено")
                }
            )
            updateSelected(actionButton)
        }

        val penTool = ComponentRegistry.findById<JButton>("toolbar_button_pen")
        penTool.addActionListener(::baseToolActionListener)

        val starTool = ComponentRegistry.findById<JButton>("toolbar_button_star")
        starTool.addActionListener(::baseToolActionListener)

        val regularTool = ComponentRegistry.findById<JButton>("toolbar_button_regular")
        regularTool.addActionListener(::baseToolActionListener)

        val lineTool = ComponentRegistry.findById<JButton>("toolbar_button_line")
        lineTool.addActionListener(::baseToolActionListener)

        val fillTool = ComponentRegistry.findById<JButton>("toolbar_button_fill")
        fillTool.addActionListener(::baseToolActionListener)

        val clearAllTool = ComponentRegistry.findById<JButton>("toolbar_button_clear_all")
        clearAllTool.addActionListener {
            context.components.field.setWhite()
        }

        val eraser = ComponentRegistry.findById<JButton>("toolbar_button_eraser")
        eraser.addActionListener(::baseToolActionListener)

        val options = ComponentRegistry.findById<JButton>("toolbar_button_options")
        val optionsPanel = OptionsPanel(context)
        options.addActionListener {
            val confirm = JOptionPane.showConfirmDialog(
                application,
                optionsPanel,
                StringResource.loadString("dialogue_options_label", context.properties.locale),
                JOptionPane.OK_CANCEL_OPTION
            )
            if (JOptionPane.OK_OPTION == confirm) {
                val size = optionsPanel.penSize
                context.components.field.setThickness(size)
                context.components.field.setRegularParameters(
                    optionsPanel.angle,
                    optionsPanel.numOfVertices,
                    optionsPanel.bigRadius,
                    optionsPanel.smallRadius
                )
            }
        }

        val redColor = ComponentRegistry.findById<JButton>("toolbar_button_red")
        redColor.addActionListener { context.components.field.setColor(Color.RED) }

        val greenColor = ComponentRegistry.findById<JButton>("toolbar_button_green")
        greenColor.addActionListener { context.components.field.setColor(Color.GREEN) }

        val blueColor = ComponentRegistry.findById<JButton>("toolbar_button_blue")
        blueColor.addActionListener { context.components.field.setColor(Color.BLUE) }

        val blackColor = ComponentRegistry.findById<JButton>("toolbar_button_black")
        blackColor.addActionListener { context.components.field.setColor(Color.BLACK) }

        val palletButton = ComponentRegistry.findById<JButton>("toolbar_button_palette")
        palletButton.addActionListener {
            val colorNew = JColorChooser.showDialog(
                null,
                StringResource.loadString("dialogue_pallet_label", context.properties.locale),
                Color.BLACK
            )
            colorNew?.let {
                context.components.field.setColor(it)
                palletButton.background = it
            }
        }

        val undoButton = ComponentRegistry.findById<JButton>("toolbar_button_undo")
        undoButton.addActionListener {
            context.components.field.undo()
        }

        /*View Menu action listeners*/
        val eraserItem = ComponentRegistry.findById<JMenuItem>("menu_tools_button_eraser")
        eraserItem.addActionListener(::baseToolActionListener)

        val clearAllItem = ComponentRegistry.findById<JMenuItem>("menu_tools_button_clear")
        clearAllItem.addActionListener {
            context.components.field.setWhite()
        }

        val penItem = ComponentRegistry.findById<JMenuItem>("menu_tools_button_pen")
        penItem.addActionListener(::baseToolActionListener)

        val lineItem = ComponentRegistry.findById<JMenuItem>("menu_tools_button_line")
        lineItem.addActionListener(::baseToolActionListener)

        val regularItem = ComponentRegistry.findById<JMenuItem>("menu_tools_button_regular")
        regularItem.addActionListener(::baseToolActionListener)

        val starItem = ComponentRegistry.findById<JMenuItem>("menu_tools_button_star")
        starItem.addActionListener(::baseToolActionListener)

        val fillItem = ComponentRegistry.findById<JMenuItem>("menu_tools_button_fill")
        fillItem.addActionListener(::baseToolActionListener)

        val fieldSize = ComponentRegistry.findById<JMenuItem>("menu_tools_button_resize")
        fieldSize.addActionListener {
            val resizePanel = ComponentRegistry.findById<JPanel>("dialogue_resize")
            val widthSpinner = (resizePanel.getComponent(1) as JSpinner)
            val heightSpinner = (resizePanel.getComponent(3) as JSpinner)

            val confirm = JOptionPane.showConfirmDialog(
                null,
                resizePanel,
                StringResource.loadString("dialogue_resize_label", context.properties.locale),
                JOptionPane.OK_CANCEL_OPTION
            )

            val newWidth = widthSpinner.value as Int
            val newHeight = heightSpinner.value as Int

            if (JOptionPane.OK_OPTION == confirm) {
                context.components.field.resizeImage(newWidth, newHeight)
                context.components.scrollPane.updateUI()
            }
        }


        val aboutItem = ComponentRegistry.findById<JMenuItem>("menu_about_button_about")
        aboutItem.addActionListener {
            val instructionDialog = InstructionDialog(application)
            instructionDialog.isVisible = true
        }
    }
}
