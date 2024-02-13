package ru.nsu.e.shelbogashev.wireframe.utils;

/**
 * Предоставляет набор констант и методов для управления настройками приложения.
 */
public class Settings {
    /**
     * Эта константа представляет длину одного сегмента
     * координатной плоскости в пикселях.
     */
    public static final int DEFAULT_INDENT = 30;

    /**
     * Эта константа представляет радиус круга точки-якоря в пикселях.
     */
    public static final int RADIUS = 10;
    /**
     * Количество сегментов, на которые будет разделен сегмент [0, 1].
     */
    public static int SEGMENTS_NUM = 10;
    /**
     * Это количество продольных генератрис.
     */
    private static int generatrixNum = 5;
    private static int circlesNum = 4;
    private static int circlesAccuracy = 3;

    /**
     * Возвращает количество продольных генератрис.
     *
     * @return Количество продольных генератрис.
     */
    public static int getGeneratrixNum() {
        return generatrixNum;
    }

    /**
     * Устанавливает новое значение количества продольных генератрис.
     *
     * @param generatrixNum Новое значение количества продольных генератрис.
     */
    public static void setGeneratrixNum(int generatrixNum) {
        Settings.generatrixNum = generatrixNum;
    }

    /**
     * Возвращает количество круговых генератрис.
     *
     * @return Количество круговых генератрис.
     */
    public static int getCirclesNum() {
        return circlesNum;
    }

    /**
     * Устанавливает новое значение количества круговых генератрис.
     *
     * @param circlesNum Новое значение количества круговых генератрис.
     */
    public static void setCirclesNum(int circlesNum) {
        Settings.circlesNum = circlesNum;
    }

    /**
     * Возвращает точность круговых генератрис.
     *
     * @return Точность круговых генератрис.
     */
    public static int getCirclesAccuracy() {
        return circlesAccuracy;
    }

    /**
     * Устанавливает новое значение точности круговых генератрис.
     *
     * @param circlesAccuracy Новое значение точности круговых генератрис.
     */
    public static void setCirclesAccuracy(int circlesAccuracy) {
        Settings.circlesAccuracy = circlesAccuracy;
    }
}
