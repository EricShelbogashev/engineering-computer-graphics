package ru.nsu.e.shelbogashev.art.studio.paint

import ru.nsu.e.shelbogashev.art.studio.paint.frames.LoadFrame
import ru.nsu.e.shelbogashev.art.studio.paint.frames.OptionsPanel
import ru.nsu.e.shelbogashev.art.studio.paint.frames.SaveFrame
import ru.nsu.e.shelbogashev.art.studio.paint.support.IconResource
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Dimension
import java.awt.event.ActionEvent
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.io.IOException
import javax.swing.*

class MainFrame : JFrame("Paint") {
    private val field: DrawField
    private val saveFrame: SaveFrame
    private val loadFrame: LoadFrame
    private val optionsPanel: OptionsPanel

    private val anyColorButton: JButton
    private val curToolButton: JButton

    private val penItem: JRadioButtonMenuItem
    private val lineItem: JRadioButtonMenuItem
    private val polygonItem: JRadioButtonMenuItem
    private val starItem: JRadioButtonMenuItem
    private val fillItem: JRadioButtonMenuItem
    private val eraserItem: JRadioButtonMenuItem

    init {
        defaultCloseOperation = DO_NOTHING_ON_CLOSE
        setBounds(400, 100, 640, 480)
        minimumSize = Dimension(640, 480)

        field = DrawField()
        saveFrame = SaveFrame(field)
        loadFrame = LoadFrame(field)
        optionsPanel = OptionsPanel()

        field.isVisible = true
        val scrollPane = JScrollPane(field)
        field.preferredSize = Dimension(640, 480)
        add(scrollPane)

        val penIcon = IconResource.PEN.loadIcon()
        val optionsIcon = IconResource.OPTIONS.loadIcon()
        val cleanIcon = IconResource.CLEAN.loadIcon()
        val eraserIcon = IconResource.ERASER.loadIcon()
        val lineIcon = IconResource.LINE.loadIcon()
        val starIcon = IconResource.STAR.loadIcon()
        val fillIcon = IconResource.FILL.loadIcon()
        val anyColorIcon = IconResource.ANY_COLOR.loadIcon()
        val polygonIcon = IconResource.POLYGON.loadIcon()
        val backIcon = IconResource.BACK.loadIcon()
        val bookIcon = IconResource.BOOK.loadIcon()

        /*TOOLBAR SECTION*/
        val toolBar = JToolBar()
        toolBar.isFloatable = false
        toolBar.isRollover = false
        toolBar.isVisible = true

        add(toolBar, BorderLayout.NORTH)

        val options = JButton(optionsIcon)
        options.isFocusPainted = false
        toolBar.add(options)

        val cleanField = JButton(cleanIcon)
        cleanField.isFocusPainted = false
        toolBar.add(cleanField)

        val eraser = JButton(eraserIcon)
        eraser.isFocusPainted = false
        toolBar.add(eraser)

        val penTool = JButton(penIcon)
        penTool.isFocusPainted = false
        toolBar.add(penTool)

        val lineTool = JButton(lineIcon)
        lineTool.isFocusPainted = false
        toolBar.add(lineTool)

        val polygonTool = JButton(polygonIcon)
        polygonTool.isFocusPainted = false
        toolBar.add(polygonTool)

        val starTool = JButton(starIcon)
        starTool.isFocusPainted = false
        toolBar.add(starTool)

        val fillTool = JButton(fillIcon)
        fillTool.isFocusPainted = false
        toolBar.add(fillTool)

        val redColor = JButton("     ")
        redColor.isFocusPainted = false
        redColor.background = Color.RED
        toolBar.add(redColor)

        val greenColor = JButton("     ")
        greenColor.isFocusPainted = false
        greenColor.background = Color.GREEN
        toolBar.add(greenColor)

        val blueColor = JButton("     ")
        blueColor.isFocusPainted = false
        blueColor.background = Color.BLUE
        toolBar.add(blueColor)

        val blackColor = JButton("     ")
        blackColor.isFocusPainted = false
        blackColor.background = Color.BLACK
        toolBar.add(blackColor)

        anyColorButton = JButton(anyColorIcon)
        anyColorButton.isFocusPainted = false
        toolBar.add(anyColorButton)

        val backTool = JButton(backIcon)
        backTool.isFocusPainted = false
        toolBar.add(backTool)

        val curToolLabel = JLabel("Tool:")
        toolBar.add(curToolLabel)

        curToolButton = JButton(penIcon)
        curToolButton.isFocusPainted = false
        toolBar.add(curToolButton)

        /*---------------*/

        /*FILE MENUBAR SECTION*/
        val menuBar = JMenuBar()
        val fileMenu = JMenu("File")
        menuBar.add(fileMenu)

        val saveItem = JMenuItem("Save")

        val loadItem = JMenuItem("Load")

        val exitItem = JMenuItem("Exit")

        fileMenu.add(exitItem)
        fileMenu.add(loadItem)
        fileMenu.add(saveItem)

        /*---------------------*/

        /*VIEW MENUBAR SECTION*/
        val viewMenu = JMenu("View")
        menuBar.add(viewMenu)

        val tools = ButtonGroup()

        eraserItem = JRadioButtonMenuItem("Eraser")

        val clearItem = JMenuItem("Clear")

        penItem = JRadioButtonMenuItem("Pen")

        lineItem = JRadioButtonMenuItem("Line")

        polygonItem = JRadioButtonMenuItem("Polygon")

        starItem = JRadioButtonMenuItem("Star")

        fillItem = JRadioButtonMenuItem("Fill")

        val fieldSize = JMenuItem("Resize")

        viewMenu.add(penItem)
        viewMenu.add(lineItem)
        viewMenu.add(polygonItem)
        viewMenu.add(starItem)
        viewMenu.add(fillItem)
        viewMenu.add(eraserItem)
        viewMenu.add(clearItem)
        viewMenu.add(fieldSize)

        tools.add(penItem)
        tools.add(lineItem)
        tools.add(polygonItem)
        tools.add(starItem)
        tools.add(fillItem)
        tools.add(eraserItem)

        /*------------------------*/

        /*ABOUT MENUBAR SECTION*/
        val aboutMenu = JMenu("About")
        menuBar.add(aboutMenu)

        val aboutItem = JMenuItem("About")
        aboutMenu.add(aboutItem)

        /*----------------------*/

        /*FILE ACTION LISTENERS*/
        exitItem.addActionListener { e: ActionEvent? -> System.exit(0) }

        saveItem.addActionListener { e: ActionEvent? ->
            try {
                saveFrame.saveImage()
            } catch (ex: IOException) {
                throw RuntimeException(ex)
            }
        }

        loadItem.addActionListener { e: ActionEvent? ->
            try {
                loadFrame.loadImage()
            } catch (ex: IOException) {
                throw RuntimeException(ex)
            }
        }

        /*---------------------*/

        /*TOOLBAR ACTION LISTENERS*/
        penTool.addActionListener { e: ActionEvent? ->
            field.setPenStyle(DrawField.PenStyle.PEN)
            curToolButton.icon = penIcon

            penItem.isSelected = true
            lineItem.isSelected = false
            eraserItem.isSelected = false
            polygonItem.isSelected = false
            starItem.isSelected = false
            fillItem.isSelected = false
        }

        starTool.addActionListener { e: ActionEvent? ->
            field.setPenStyle(DrawField.PenStyle.STAR)
            curToolButton.icon = starIcon

            penItem.isSelected = false
            lineItem.isSelected = false
            eraserItem.isSelected = false
            polygonItem.isSelected = false
            starItem.isSelected = true
            fillItem.isSelected = false
        }

        polygonTool.addActionListener { e: ActionEvent? ->
            field.setPenStyle(DrawField.PenStyle.POLYGON)
            curToolButton.icon = polygonIcon

            penItem.isSelected = false
            lineItem.isSelected = false
            eraserItem.isSelected = false
            polygonItem.isSelected = true
            starItem.isSelected = false
            fillItem.isSelected = false
        }

        lineTool.addActionListener { e: ActionEvent? ->
            field.setPenStyle(DrawField.PenStyle.LINE)
            curToolButton.icon = lineIcon

            penItem.isSelected = false
            lineItem.isSelected = true
            eraserItem.isSelected = false
            polygonItem.isSelected = false
            starItem.isSelected = false
            fillItem.isSelected = false
        }

        fillTool.addActionListener { e: ActionEvent? ->
            field.setPenStyle(DrawField.PenStyle.FILL)
            curToolButton.icon = fillIcon

            penItem.isSelected = false
            lineItem.isSelected = false
            eraserItem.isSelected = false
            polygonItem.isSelected = false
            starItem.isSelected = false
            fillItem.isSelected = true
        }

        cleanField.addActionListener { e: ActionEvent? ->
            field.setWhite()
        }

        options.addActionListener { e: ActionEvent? ->
            val confirm = JOptionPane.showConfirmDialog(this, optionsPanel, "Options", JOptionPane.OK_CANCEL_OPTION)
            if (JOptionPane.OK_OPTION == confirm) {
                val size = optionsPanel.penSize
                field.setThickness(size)
                field.setPolygonParameters(
                    optionsPanel.angle,
                    optionsPanel.numOfVertices,
                    optionsPanel.bigRadius,
                    optionsPanel.smallRadius
                )
            }
        }

        redColor.addActionListener { e: ActionEvent? ->
            field.setColor(Color.RED)
        }

        greenColor.addActionListener { e: ActionEvent? ->
            field.setColor(Color.GREEN)
        }

        blueColor.addActionListener { e: ActionEvent? ->
            field.setColor(Color.BLUE)
        }

        blackColor.addActionListener { e: ActionEvent? ->
            field.setColor(Color.BLACK)
        }

        eraser.addActionListener { e: ActionEvent? ->
            field.setPenStyle(DrawField.PenStyle.ERASER)
            curToolButton.icon = eraserIcon
        }

        anyColorButton.addActionListener { e: ActionEvent? ->
            val newColor = JColorChooser.showDialog(null, "Colors", Color.BLACK)
            field.setColor(newColor)
            anyColorButton.background = newColor
        }

        backTool.addActionListener { e: ActionEvent? ->
            field.back()
        }

        /*-----------------------------*/
        this.addWindowListener(object : WindowAdapter() {
            override fun windowClosing(e: WindowEvent) {
                //super.windowClosed(e);
                if (JOptionPane.showConfirmDialog(
                        null,
                        "Are you sure you want to close programm?",
                        "Close",
                        JOptionPane.YES_NO_OPTION
                    ) == JOptionPane.YES_OPTION
                ) {
                    System.exit(0)
                }
            }
        })

        /*----------------*/

        /*View Menu action listeners*/
        eraserItem.addActionListener { e: ActionEvent? ->
            field.setPenStyle(DrawField.PenStyle.ERASER)
            curToolButton.icon = eraserIcon
        }

        clearItem.addActionListener { e: ActionEvent? ->
            field.setWhite()
        }

        penItem.addActionListener { e: ActionEvent? ->
            field.setPenStyle(DrawField.PenStyle.PEN)
            curToolButton.icon = penIcon
        }

        lineItem.addActionListener { e: ActionEvent? ->
            field.setPenStyle(DrawField.PenStyle.LINE)
            curToolButton.icon = lineIcon
        }

        polygonItem.addActionListener { e: ActionEvent? ->
            field.setPenStyle(DrawField.PenStyle.POLYGON)
            curToolButton.icon = polygonIcon
        }

        starItem.addActionListener { e: ActionEvent? ->
            field.setPenStyle(DrawField.PenStyle.STAR)
            curToolButton.icon = starIcon
        }

        fillItem.addActionListener { e: ActionEvent? ->
            field.setPenStyle(DrawField.PenStyle.FILL)
            curToolButton.icon = fillIcon
        }

        val widthModel = SpinnerNumberModel(640, 640, 2100, 1)
        val widthField = JSpinner(widthModel)
        val heightModel = SpinnerNumberModel(480, 480, 2000, 1)
        val heightField = JSpinner(heightModel)
        val resizePanel = JPanel()
        resizePanel.add(JLabel("Width: "))
        resizePanel.add(widthField)
        resizePanel.add(JLabel("Height:"))
        resizePanel.add(heightField)

        fieldSize.addActionListener { e: ActionEvent? ->
            val confirm = JOptionPane.showConfirmDialog(null, resizePanel, "Resize", JOptionPane.OK_CANCEL_OPTION)
            if (JOptionPane.OK_OPTION == confirm) {
                field.resizeImage(widthField.value as Int, heightField.value as Int)
                scrollPane.updateUI()
            }
        }

        aboutItem.addActionListener { e: ActionEvent? ->
            JOptionPane.showConfirmDialog(
                null,
                """Как работать с этой программой:

Кнопка с шестеренками откроет параметры программы. Вы можете установить размер пера, количество вершин для форм, угол (для поворота
форм) и радиус для форм.

Кнопка с метлой очищает весь холст.

Кнопка с ручкой позволяет вам свободно рисовать. Толщину можно изменить в параметрах.

Кнопка с линией рисует линию. Вам нужно выполнить два клика по холсту: первый клик - начало линии, второй
клик - конец линии. Толщину можно изменить в параметрах. Если толщина = 1, то линия будет нарисована с
использованием алгоритма Брезенхема.

Кнопка с многоугольником рисует форму с n вершинами. Количество вершин можно установить в параметрах. Также в параметрах
можно установить радиус формы (это делает форму больше) и угол, который поворачивает форму.

Кнопка со звездой рисует звезду с n вершинами. Количество вершин можно установить в параметрах. Также в параметрах
можно установить угол звезды, который ее поворачивает.

Кнопка с ведром заполняет область, по которой вы кликнули, выбранным цветом.

Кнопки с цветами меняют цвет.

Кнопка с палитрой позволяет выбрать любой цвет, который вам нужен.

Кнопка со стрелкой отменяет предыдущий ход.""",
                "About",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                bookIcon
            )
        }
        /*-----------*/
        this.jMenuBar = menuBar
        this.isVisible = true
        this.pack()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            MainFrame()
        }
    }
}