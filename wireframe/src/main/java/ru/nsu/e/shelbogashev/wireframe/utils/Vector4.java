package ru.nsu.e.shelbogashev.wireframe.utils;

/**
 * Представляет четырехмерный вектор с координатами (x, y, z, w).
 */
public class Vector4 {
    private final double w;
    private double x;
    private double y;
    private double z;

    /**
     * Создает новый четырехмерный вектор с заданными координатами и w = 1.
     *
     * @param x Координата x.
     * @param y Координата y.
     * @param z Координата z.
     * @param w Координата w.
     */
    public Vector4(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = 1; // Убедимся, что w изначально установлен в 1
    }

    /**
     * Возвращает координату x вектора.
     *
     * @return Координата x.
     */
    public double getX() {
        return x;
    }

    /**
     * Возвращает координату y вектора.
     *
     * @return Координата y.
     */
    public double getY() {
        return y;
    }

    /**
     * Возвращает координату z вектора.
     *
     * @return Координата z.
     */
    public double getZ() {
        return z;
    }

    /**
     * Возвращает координату w вектора.
     *
     * @return Координата w.
     */
    public double getW() {
        return w;
    }

    /**
     * Нормализует вектор, делая его длину равной 1.
     */
    public void normalize() {
        double norm = Math.sqrt(x * x + y * y + z * z);
        x /= norm;
        y /= norm;
        z /= norm;
    }

    @Override
    public String toString() {
        return x + " " + y + " " + z + " " + w;
    }
}