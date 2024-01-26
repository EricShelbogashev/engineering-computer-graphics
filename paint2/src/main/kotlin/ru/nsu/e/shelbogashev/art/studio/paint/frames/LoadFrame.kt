package ru.nsu.e.shelbogashev.art.studio.paint.frames

import ru.nsu.e.shelbogashev.art.studio.paint.DrawField
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
        // Создаем диалоговое окно для выбора файла
        val load = FileDialog(this, "Загрузить изображение", FileDialog.LOAD)
        load.file = "*.png; *.jpg; *.jpeg;"
        load.isVisible = true

        // Получаем путь к выбранному файлу с использованием безопасного вызова
        val file: String? = load.directory?.let { directory ->
            load.file?.let { file -> directory + file }
        }

        // Если файл выбран
        if (!file.isNullOrBlank()) {
            // Читаем изображение из файла
            val newImage: BufferedImage = ImageIO.read(File(file))

            // Устанавливаем изображение в поле рисования
            drawField.setImage(newImage)
        }
    }
}