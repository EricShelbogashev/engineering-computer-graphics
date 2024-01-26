package ru.nsu.e.shelbogashev.art.studio.paint.frames

import ru.nsu.e.shelbogashev.art.studio.paint.DrawField
import java.awt.FileDialog
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO
import javax.swing.JFrame

/**
 * Класс SaveFrame представляет собой JFrame для сохранения изображения из DrawField.
 *
 * @property drawField DrawField, из которого будет сохранено изображение.
 */
class SaveFrame(field: DrawField) : JFrame() {
    private val drawField: DrawField

    /**
     * Инициализация объекта SaveFrame.
     *
     * @param field DrawField, из которого будет сохранено изображение.
     */
    init {
        this.isVisible = false // Делаем окно невидимым при создании
        setBounds(400, 100, 300, 300) // Устанавливаем размеры окна
        drawField = field
    }

    /**
     * Сохраняет изображение из DrawField в файл формата PNG.
     *
     * @throws IOException Если произошла ошибка в процессе сохранения изображения.
     */
    @Throws(IOException::class)
    fun saveImage() {
        // Создаем диалоговое окно для выбора файла и указываем тип операции (сохранение)
        val save = FileDialog(this, "Сохранить изображение", FileDialog.SAVE)
        save.isVisible = true

        // Получаем путь к выбранному файлу и добавляем расширение ".png"
        val name = save.directory + save.file + ".png"

        // Создаем объект File для сохранения изображения
        val image = File(name)

        // Записываем изображение в файл с использованием библиотеки ImageIO
        ImageIO.write(drawField.getImage(), "png", image)
    }
}