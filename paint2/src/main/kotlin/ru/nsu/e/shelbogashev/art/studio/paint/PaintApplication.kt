
import com.formdev.flatlaf.themes.FlatMacLightLaf
import ru.nsu.e.shelbogashev.art.studio.paint.context.ApplicationComponents
import ru.nsu.e.shelbogashev.art.studio.paint.context.ApplicationContext
import ru.nsu.e.shelbogashev.art.studio.paint.context.ApplicationProperties
import ru.nsu.e.shelbogashev.art.studio.paint.initializer.ActionHandlers
import ru.nsu.e.shelbogashev.art.studio.paint.initializer.MenuBarInitializer
import ru.nsu.e.shelbogashev.art.studio.paint.initializer.ToolBarInitializer
import ru.nsu.e.shelbogashev.art.studio.paint.model.DrawField
import ru.nsu.e.shelbogashev.art.studio.paint.model.support.StringResource
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.util.*
import javax.swing.JFrame
import javax.swing.JOptionPane
import javax.swing.JScrollPane
import javax.swing.SwingUtilities
import kotlin.system.exitProcess


class PaintApplication(private val properties: ApplicationProperties) : JFrame(properties.windowTitle) {
    private val components: ApplicationComponents
    private val context: ApplicationContext

    init {
        locale = properties.locale
        val field = DrawField()
        this.size = properties.windowSize
        field.preferredSize = Dimension(this.size.width, this.size.height);

        components = ApplicationComponents(
            toolbar = ToolBarInitializer().initToolBar(this),
            menubar = MenuBarInitializer().initMenuBar(this),
            scrollPane = JScrollPane(field).apply {
                this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            },
            field = field
        )
        context = ApplicationContext(properties, components)
        configureWindow()
        layoutComponents()
        ActionHandlers().setupActionListeners(this, context)

        addWindowListener(object : WindowAdapter() {
            override fun windowClosing(e: WindowEvent) {
                if (JOptionPane.showConfirmDialog(
                        null,
                        StringResource.loadString("dialogue_close_message", locale),
                        StringResource.loadString("dialogue_close_label", locale),
                        JOptionPane.YES_NO_OPTION
                    ) == JOptionPane.YES_OPTION
                ) {
                    exitProcess(0)
                }
            }
        })
    }

    private fun configureWindow() {
        this.size = properties.windowSize
        this.minimumSize = properties.minimumWindowSize
        this.defaultCloseOperation = properties.defaultCloseOperation
    }

    private fun layoutComponents() {
        this.layout = BorderLayout()
        this.add(components.toolbar, BorderLayout.NORTH)
        this.add(components.scrollPane, BorderLayout.CENTER)
        this.jMenuBar = components.menubar
        this.pack()
        this.isVisible = true
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            FlatMacLightLaf.setup()
            // Можно добавить сохраняемость настроек.
            val appProperties = ApplicationProperties(locale = Locale.of("ru"))
            SwingUtilities.invokeLater { PaintApplication(appProperties) }
        }
    }
}