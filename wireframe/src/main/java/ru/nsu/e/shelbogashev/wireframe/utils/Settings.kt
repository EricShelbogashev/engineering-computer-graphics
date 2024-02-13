package ru.nsu.e.shelbogashev.wireframe.utils

/**
 * Предоставляет набор констант и методов для управления настройками приложения.
 */
object Settings {
    /**
     * Длина одного сегмента координатной плоскости в пикселях.
     */
    const val DEFAULT_INDENT: Int = 30

    /**
     * Радиус круга точки-якоря в пикселях.
     */
    const val RADIUS: Int = 10

    /**
     * Количество сегментов, на которые будет разделен сегмент [0, 1].
     */
    const val SEGMENTS_NUM: Int = 10

    /**
     * Количество продольных генератрис.
     */
    var generatrixNum: Int = 5

    /**
     * Количество круговых генератрис.
     */
    var circlesNum: Int = 4

    /**
     * Точность круговых генератрис.
     */
    var circlesAccuracy: Int = 3
}
