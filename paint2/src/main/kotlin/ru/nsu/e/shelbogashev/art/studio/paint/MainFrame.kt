package ru.nsu.e.shelbogashev.art.studio.paint

import ru.nsu.e.shelbogashev.art.studio.paint.frames.LoadFrame
import ru.nsu.e.shelbogashev.art.studio.paint.frames.OptionsPanel
import ru.nsu.e.shelbogashev.art.studio.paint.frames.SaveFrame
import ru.nsu.e.shelbogashev.art.studio.paint.support.IconResource
import ru.nsu.e.shelbogashev.art.studio.paint.support.StringResource
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Dimension
import java.awt.event.ActionEvent
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.io.IOException
import java.util.*
import java.util.stream.Stream
import javax.swing.*

class MainFrame : JFrame("Paint") {
    private val field: DrawField
    private val saveFrame: SaveFrame
    private val loadFrame: LoadFrame
    private val optionsPanel: OptionsPanel

    private val anyColorButton: JButton

    private val penItem: JRadioButtonMenuItem
    private val lineItem: JRadioButtonMenuItem
    private val polygonItem: JRadioButtonMenuItem
    private val starItem: JRadioButtonMenuItem
    private val fillItem: JRadioButtonMenuItem
    private val eraserItem: JRadioButtonMenuItem

    private var locale = Locale.of("ru")

    private fun buildToolbarButton(
        icon: Icon? = null,
        toolTipText: String? = null,
        background: Color? = null
    ): JButton {
        val size = Dimension(28, 24)
        val button = JButton().apply {
            isFocusPainted = false
            preferredSize = size
            maximumSize = size
            isOpaque = true
            isBorderPainted = false
            this.background = background
            this.toolTipText = toolTipText
            this.icon = icon
        }
        return button
    }

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
        val regularIcon = IconResource.REGULAR.loadIcon()
        val undoIcon = IconResource.BACK.loadIcon()
        val bookIcon = IconResource.BOOK.loadIcon()

        // TOOLBAR SECTION
        val toolBar = JToolBar().apply {
            isFloatable = false
            isRollover = false
            isVisible = true
        }

        add(toolBar, BorderLayout.NORTH)

        val options = buildToolbarButton(optionsIcon, StringResource.loadString("canvas_button_options", locale))
        val cleanField = buildToolbarButton(cleanIcon, StringResource.loadString("canvas_button_clear_all", locale))
        val eraser = buildToolbarButton(eraserIcon, StringResource.loadString("canvas_button_eraser", locale))
        val penTool = buildToolbarButton(penIcon, StringResource.loadString("canvas_button_pen", locale))
        val lineTool = buildToolbarButton(lineIcon, StringResource.loadString("canvas_button_line", locale))
        val regularTool = buildToolbarButton(regularIcon, StringResource.loadString("canvas_button_regular", locale))
        val starTool = buildToolbarButton(starIcon, StringResource.loadString("canvas_button_star", locale))
        val fillTool = buildToolbarButton(fillIcon, StringResource.loadString("canvas_button_fill", locale))
        val redColor = buildToolbarButton(background = Color.RED)
        val greenColor = buildToolbarButton(background = Color.GREEN)
        val blueColor = buildToolbarButton(background = Color.BLUE)
        val blackColor = buildToolbarButton(background = Color.BLACK)
        anyColorButton = buildToolbarButton(anyColorIcon, StringResource.loadString("canvas_button_pallet", locale))
        val undoButton = buildToolbarButton(undoIcon, StringResource.loadString("canvas_button_undo", locale))
        val selectedTool = JLabel("selected tool", penIcon, SwingConstants.CENTER)
        selectedTool.toolTipText = StringResource.loadString("canvas_button_current_tool", locale)

        Stream.of(
            options,
            cleanField,
            eraser,
            penTool,
            lineTool,
            regularTool,
            starTool,
            fillTool,
            redColor,
            greenColor,
            blueColor,
            blackColor,
            anyColorButton,
            undoButton,
            selectedTool
        ).forEach(toolBar::add)

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
            selectedTool.icon = penIcon

            penItem.isSelected = true
            lineItem.isSelected = false
            eraserItem.isSelected = false
            polygonItem.isSelected = false
            starItem.isSelected = false
            fillItem.isSelected = false
        }

        starTool.addActionListener { e: ActionEvent? ->
            field.setPenStyle(DrawField.PenStyle.STAR)
            selectedTool.icon = starIcon

            penItem.isSelected = false
            lineItem.isSelected = false
            eraserItem.isSelected = false
            polygonItem.isSelected = false
            starItem.isSelected = true
            fillItem.isSelected = false
        }

        regularTool.addActionListener { e: ActionEvent? ->
            field.setPenStyle(DrawField.PenStyle.POLYGON)
            selectedTool.icon = regularIcon

            penItem.isSelected = false
            lineItem.isSelected = false
            eraserItem.isSelected = false
            polygonItem.isSelected = true
            starItem.isSelected = false
            fillItem.isSelected = false
        }

        lineTool.addActionListener { e: ActionEvent? ->
            field.setPenStyle(DrawField.PenStyle.LINE)
            selectedTool.icon = lineIcon

            penItem.isSelected = false
            lineItem.isSelected = true
            eraserItem.isSelected = false
            polygonItem.isSelected = false
            starItem.isSelected = false
            fillItem.isSelected = false
        }

        fillTool.addActionListener { e: ActionEvent? ->
            field.setPenStyle(DrawField.PenStyle.FILL)
            selectedTool.icon = fillIcon

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
            selectedTool.icon = eraserIcon
        }

        anyColorButton.addActionListener { e: ActionEvent? ->
            val newColor = JColorChooser.showDialog(null, "Colors", Color.BLACK)
            field.setColor(newColor)
            anyColorButton.background = newColor
        }

        undoButton.addActionListener { e: ActionEvent? ->
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
            selectedTool.icon = eraserIcon
        }

        clearItem.addActionListener { e: ActionEvent? ->
            field.setWhite()
        }

        penItem.addActionListener { e: ActionEvent? ->
            field.setPenStyle(DrawField.PenStyle.PEN)
            selectedTool.icon = penIcon
        }

        lineItem.addActionListener { e: ActionEvent? ->
            field.setPenStyle(DrawField.PenStyle.LINE)
            selectedTool.icon = lineIcon
        }

        polygonItem.addActionListener { e: ActionEvent? ->
            field.setPenStyle(DrawField.PenStyle.POLYGON)
            selectedTool.icon = regularIcon
        }

        starItem.addActionListener { e: ActionEvent? ->
            field.setPenStyle(DrawField.PenStyle.STAR)
            selectedTool.icon = starIcon
        }

        fillItem.addActionListener { e: ActionEvent? ->
            field.setPenStyle(DrawField.PenStyle.FILL)
            selectedTool.icon = fillIcon
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