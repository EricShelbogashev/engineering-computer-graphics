package ru.nsu.e.shelbogashev.art.studio.paint.initializer

import PaintApplication
import ru.nsu.e.shelbogashev.art.studio.paint.context.ApplicationContext

class ActionHandlers {
    fun setupActionListeners(application: PaintApplication, context: ApplicationContext) {
//        /*FILE ACTION LISTENERS*/
//        context.components.toolbar.exitItem.addActionListener { e: ActionEvent? -> System.exit(0) }
//
//        saveItem.addActionListener { e: ActionEvent? ->
//            try {
//                saveFrame.saveImage()
//            } catch (ex: IOException) {
//                throw RuntimeException(ex)
//            }
//        }
//
//        loadItem.addActionListener { e: ActionEvent? ->
//            try {
//                loadFrame.loadImage()
//            } catch (ex: IOException) {
//                throw RuntimeException(ex)
//            }
//        }
//
//        /*---------------------*/
//
//        /*TOOLBAR ACTION LISTENERS*/
//        penTool.addActionListener { e: ActionEvent? ->
//            field.setPenStyle(DrawField.PenStyle.PEN)
//            selectedTool.icon = penIcon
//
//            penItem.isSelected = true
//            lineItem.isSelected = false
//            eraserItem.isSelected = false
//            polygonItem.isSelected = false
//            starItem.isSelected = false
//            fillItem.isSelected = false
//        }
//
//        starTool.addActionListener { e: ActionEvent? ->
//            field.setPenStyle(DrawField.PenStyle.STAR)
//            selectedTool.icon = starIcon
//
//            penItem.isSelected = false
//            lineItem.isSelected = false
//            eraserItem.isSelected = false
//            polygonItem.isSelected = false
//            starItem.isSelected = true
//            fillItem.isSelected = false
//        }
//
//        regularTool.addActionListener { e: ActionEvent? ->
//            field.setPenStyle(DrawField.PenStyle.POLYGON)
//            selectedTool.icon = regularIcon
//
//            penItem.isSelected = false
//            lineItem.isSelected = false
//            eraserItem.isSelected = false
//            polygonItem.isSelected = true
//            starItem.isSelected = false
//            fillItem.isSelected = false
//        }
//
//        lineTool.addActionListener { e: ActionEvent? ->
//            field.setPenStyle(DrawField.PenStyle.LINE)
//            selectedTool.icon = lineIcon
//
//            penItem.isSelected = false
//            lineItem.isSelected = true
//            eraserItem.isSelected = false
//            polygonItem.isSelected = false
//            starItem.isSelected = false
//            fillItem.isSelected = false
//        }
//
//        fillTool.addActionListener { e: ActionEvent? ->
//            field.setPenStyle(DrawField.PenStyle.FILL)
//            selectedTool.icon = fillIcon
//
//            penItem.isSelected = false
//            lineItem.isSelected = false
//            eraserItem.isSelected = false
//            polygonItem.isSelected = false
//            starItem.isSelected = false
//            fillItem.isSelected = true
//        }
//
//        cleanField.addActionListener { e: ActionEvent? ->
//            field.setWhite()
//        }
//
//        options.addActionListener {
//            val confirm = JOptionPane.showConfirmDialog(
//                this,
//                optionsPanel,
//                StringResource.loadString("dialogue_options_label", locale),
//                JOptionPane.OK_CANCEL_OPTION
//            )
//            if (JOptionPane.OK_OPTION == confirm) {
//                val size = optionsPanel.penSize
//                field.setThickness(size)
//                field.setRegularParameters(
//                    optionsPanel.angle,
//                    optionsPanel.numOfVertices,
//                    optionsPanel.bigRadius,
//                    optionsPanel.smallRadius
//                )
//            }
//        }
//
//        redColor.addActionListener { e: ActionEvent? ->
//            field.setColor(Color.RED)
//        }
//
//        greenColor.addActionListener { e: ActionEvent? ->
//            field.setColor(Color.GREEN)
//        }
//
//        blueColor.addActionListener { e: ActionEvent? ->
//            field.setColor(Color.BLUE)
//        }
//
//        blackColor.addActionListener { e: ActionEvent? ->
//            field.setColor(Color.BLACK)
//        }
//
//        eraser.addActionListener { e: ActionEvent? ->
//            field.setPenStyle(DrawField.PenStyle.ERASER)
//            selectedTool.icon = eraserIcon
//        }
//
//        anyColorButton.addActionListener { e: ActionEvent? ->
//            val newColor = JColorChooser.showDialog(null, "Colors", Color.BLACK)
//            field.setColor(newColor)
//            anyColorButton.background = newColor
//        }
//
//        undoButton.addActionListener { e: ActionEvent? ->
//            field.back()
//        }
//
//        /*View Menu action listeners*/
//        eraserItem.addActionListener { e: ActionEvent? ->
//            field.setPenStyle(DrawField.PenStyle.ERASER)
//            selectedTool.icon = eraserIcon
//        }
//
//        clearItem.addActionListener { e: ActionEvent? ->
//            field.setWhite()
//        }
//
//        penItem.addActionListener { e: ActionEvent? ->
//            field.setPenStyle(DrawField.PenStyle.PEN)
//            selectedTool.icon = penIcon
//        }
//
//        lineItem.addActionListener { e: ActionEvent? ->
//            field.setPenStyle(DrawField.PenStyle.LINE)
//            selectedTool.icon = lineIcon
//        }
//
//        polygonItem.addActionListener { e: ActionEvent? ->
//            field.setPenStyle(DrawField.PenStyle.POLYGON)
//            selectedTool.icon = regularIcon
//        }
//
//        starItem.addActionListener { e: ActionEvent? ->
//            field.setPenStyle(DrawField.PenStyle.STAR)
//            selectedTool.icon = starIcon
//        }
//
//        fillItem.addActionListener { e: ActionEvent? ->
//            field.setPenStyle(DrawField.PenStyle.FILL)
//            selectedTool.icon = fillIcon
//        }
//
//        fieldSize.addActionListener { e: ActionEvent? ->
//            val confirm = JOptionPane.showConfirmDialog(
//                null,
//                resizePanel,
//                StringResource.loadString("dialogue_resize_label", locale),
//                JOptionPane.OK_CANCEL_OPTION
//            )
//            if (JOptionPane.OK_OPTION == confirm) {
//                field.resizeImage(widthField.value as Int, heightField.value as Int)
//                scrollPane.updateUI()
//            }
//        }
//
//        aboutItem.addActionListener { e: ActionEvent? ->
//            val instructionDialog = InstructionDialog(this)
//            instructionDialog.isVisible = true
//        }
    }
}
