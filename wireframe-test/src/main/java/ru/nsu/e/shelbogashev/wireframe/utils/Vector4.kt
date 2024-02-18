package ru.nsu.e.shelbogashev.wireframe.utils

import kotlin.math.sqrt

/**
 * Представляет четырехмерный вектор с координатами (x, y, z, w).
 */
class Vector4(var x: Double, var y: Double, var z: Double) {
    val w: Double = 1.0

    /**
     * Нормализует вектор, делая его длину равной 1.
     */
    fun normalize() {
        val norm = sqrt(x * x + y * y + z * z)
        x /= norm
        y /= norm
        z /= norm
    }

    override fun toString(): String {
        return "$x $y $z $w"
    }
}