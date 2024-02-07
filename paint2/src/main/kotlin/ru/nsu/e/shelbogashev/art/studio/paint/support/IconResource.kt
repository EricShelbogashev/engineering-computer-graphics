package ru.nsu.e.shelbogashev.art.studio.paint.support

import java.util.*
import javax.swing.ImageIcon

/**
 * Перечисление IconResource представляет различные значки, используемые в приложении для рисования.
 *
 * Каждое значение перечисления имеет соответствующий путь к ресурсу значка.
 *
 * @property path Путь к ресурсу значка.
 */
enum class IconResource(private val path: String) {
    PEN("icon/pen.png"),
    OPTIONS("icon/tune.png"),
    CLEAN("icon/delete.png"),
    ERASER("icon/eraser.png"),
    LINE("icon/line.png"),
    STAR("icon/star.png"),
    FILL("icon/fill.png"),
    ANY_COLOR("icon/palette.png"),
    REGULAR("icon/regular.png"),
    BACK("icon/arrow_back.png"),
    BOOK("icon/menu_book.png");

    /**
     * Загружает ImageIcon, связанный с ресурсом значка.
     *
     * @return ImageIcon, загруженный из указанного пути ресурса.
     */
    fun loadIcon(): ImageIcon {
        return ImageIcon(Objects.requireNonNull(javaClass.classLoader.getResource(path)))
    }
}
