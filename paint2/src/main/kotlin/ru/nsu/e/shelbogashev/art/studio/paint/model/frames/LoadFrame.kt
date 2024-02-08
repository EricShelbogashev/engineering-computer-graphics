package ru.nsu.e.shelbogashev.art.studio.paint.model.frames

import ru.nsu.e.shelbogashev.art.studio.paint.context.ApplicationContext
import ru.nsu.e.shelbogashev.art.studio.paint.model.DrawField
import ru.nsu.e.shelbogashev.art.studio.paint.model.support.StringResource
import java.awt.FileDialog
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO
import javax.swing.JFrame
import javax.swing.JOptionPane

/**
 * Класс LoadFrame представляет собой JFrame для загрузки изображений в DrawField.
 *
 * @property drawField DrawField, в который будет установлено загруженное изображение.
 */
class LoadFrame(private val drawField: DrawField, private val context: ApplicationContext) : JFrame() {

    /**
     * Загружает изображение в DrawField.
     *
     * @throws IOException Если произошла ошибка во время процесса загрузки изображения.
     */
    @Throws(IOException::class)
    fun loadImage() {
        val load = FileDialog(this, StringResource.loadString("dialogue_load_file_title", locale), FileDialog.LOAD)
        load.file = "*.png; *.jpg; *.jpeg;"
        load.isVisible = true

        val file: String? = load.directory?.let { directory ->
            load.file?.let { file -> directory + file }
        }

        if (!file.isNullOrBlank()) {
            val newImage: BufferedImage? = ImageIO.read(File(file))
            if (newImage == null) {
                JOptionPane.showMessageDialog(
                    this,
                    StringResource.loadString("dialogue_error_loadImage", context.properties.locale),
                    StringResource.loadString("dialogue_error_loadImage_title", context.properties.locale),
                    JOptionPane.ERROR_MESSAGE
                )
                return
            }
            drawField.setImage(newImage)
        }
    }
}