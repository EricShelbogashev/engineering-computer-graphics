package ru.nsu.e.shelbogashev.wireframe.utils

/**
 * Класс для работы с матрицами.
 */
class Matrix(matrix: Array<DoubleArray>) {
    private val data = Array(matrix.size) { DoubleArray(matrix[0].size) }
    private val width = matrix[0].size
    private val height = matrix.size

    /**
     * Создает матрицу на основе двумерного массива.
     */
    init {
        for (i in matrix.indices) {
            System.arraycopy(matrix[i], 0, data[i], 0, matrix[0].size)
        }
    }

    /**
     * Умножает матрицу на вектор.
     *
     * @param point Вектор, на который происходит умножение.
     * @return Результирующий вектор.
     */
    fun multiplyMatrixVector(point: Vector4): Vector4 {
        val pointArr = doubleArrayOf(point.x, point.y, point.z, point.w)
        val result = DoubleArray(4)
        for (i in 0 until height) {
            for (j in 0 until width) {
                result[i] += data[i][j] * pointArr[j]
            }
        }

        return Vector4(result[0] / result[3], result[1] / result[3], result[2] / result[3])
    }

    /**
     * Умножает матрицу на вектор.
     *
     * @param vector Вектор, на который происходит умножение.
     * @return Результирующий вектор.
     */
    fun multiplyMatrixVector(vector: DoubleArray): DoubleArray {
        val result = DoubleArray(vector.size)

        for (i in 0 until height) {
            for (j in 0 until width) {
                result[i] += data[i][j] * vector[j]
            }
        }

        return result
    }

    /**
     * Умножает матрицу на матрицу.
     *
     * @param matrix Матрица, на которую происходит умножение.
     * @return Результирующая матрица.
     */
    fun multiplyMatrixMatrix(matrix: Matrix): Matrix {
        val resultData = Array(height) { DoubleArray(width) }

        for (i in 0 until height) {
            for (j in 0 until width) {
                for (k in 0..3) {
                    resultData[i][j] += data[i][k] * matrix.data[k][j]
                }
            }
        }

        return Matrix(resultData)
    }

    override fun toString(): String {
        val sb = StringBuilder()
        for (row in data) {
            for (element in row) {
                sb.append(element).append(" ")
            }
            sb.append("\n")
        }
        return sb.toString()
    }
}