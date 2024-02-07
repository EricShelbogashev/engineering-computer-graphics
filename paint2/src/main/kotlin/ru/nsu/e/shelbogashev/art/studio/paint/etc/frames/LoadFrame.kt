package ru.nsu.e.shelbogashev.art.studio.paint.etc.frames

import ru.nsu.e.shelbogashev.art.studio.paint.etc.DrawField
import java.awt.FileDialog
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO
import javax.swing.JFrame

/**
 * Класс LoadFrame представляет собой JFrame для загрузки изображений в DrawField.
 *
 * @property drawField DrawField, в который будет установлено загруженное изображение.
 */
class LoadFrame(private val drawField: DrawField) : JFrame() {

    /**
     * Загружает изображение в DrawField.
     *
     * @throws IOException Если произошла ошибка во время процесса загрузки изображения.
     */
    @Throws(IOException::class)
    fun loadImage() {
        val load = FileDialog(this, "Загрузить изображение", FileDialog.LOAD)
        load.file = "*.png; *.jpg; *.jpeg;"
        load.isVisible = true

        val file: String? = load.directory?.let { directory ->
            load.file?.let { file -> directory + file }
        }

        if (!file.isNullOrBlank()) {
            val newImage: BufferedImage = ImageIO.read(File(file))
            drawField.setImage(newImage)
        }
    }
}