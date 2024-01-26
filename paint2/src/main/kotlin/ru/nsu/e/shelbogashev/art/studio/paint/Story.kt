package ru.nsu.e.shelbogashev.art.studio.paint

import java.awt.image.Raster

/**
 * Класс, представляющий историю сохранений изображений.
 */
class Story {
    private val saves = ArrayList<Raster>()
    private var savesNum = 0

    /**
     * Сохраняет изображение в истории.
     *
     * @param imageToSave Изображение для сохранения.
     */
    fun saveImage(imageToSave: Raster) {
        if (savesNum > 20) {
            saves.removeFirst()
            savesNum--
        }
        saves.add(imageToSave)
        savesNum++
    }

    /**
     * Получает последнее сохраненное изображение из истории и удаляет его из списка сохранений.
     *
     * @return Последнее сохраненное изображение или null, если история пуста.
     */
    val lastSave: Raster?
        get() {
            if (savesNum > 0) {
                val lastSave = saves[savesNum - 1]
                saves.removeAt(savesNum - 1)
                savesNum--

                return lastSave
            } else {
                return null
            }
        }
}
