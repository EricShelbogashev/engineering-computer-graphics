package ru.nsu.e.shelbogashev.wireframe.utils;

/**
 * Представляет двумерную точку с координатами x и y.
 */
public class Point2D {
    private double x;
    private double y;

    /**
     * Создает новый экземпляр Point2D с указанными координатами.
     *
     * @param x Координата x.
     * @param y Координата y.
     */
    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Возвращает координату x точки.
     *
     * @return Координата x.
     */
    public double getX() {
        return x;
    }

    /**
     * Устанавливает новое значение координаты x точки.
     *
     * @param x Новое значение координаты x.
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Возвращает координату y точки.
     *
     * @return Координата y.
     */
    public double getY() {
        return y;
    }

    /**
     * Устанавливает новое значение координаты y точки.
     *
     * @param y Новое значение координаты y.
     */
    public void setY(double y) {
        this.y = y;
    }
}
