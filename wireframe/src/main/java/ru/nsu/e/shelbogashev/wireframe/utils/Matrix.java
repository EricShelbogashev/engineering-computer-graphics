package ru.nsu.e.shelbogashev.wireframe.utils;

/**
 * Класс для работы с матрицами.
 */
public class Matrix {
    private final double[][] data;
    private final int width;
    private final int height;

    /**
     * Создает матрицу на основе двумерного массива.
     *
     * @param matrix Двумерный массив, представляющий матрицу.
     */
    public Matrix(double[][] matrix) {
        this.width = matrix[0].length;
        this.height = matrix.length;
        this.data = new double[matrix.length][matrix[0].length];

        for (int i = 0; i < matrix.length; i++) {
            System.arraycopy(matrix[i], 0, this.data[i], 0, matrix[0].length);
        }
    }

    /**
     * Умножает матрицу на вектор.
     *
     * @param point Вектор, на который происходит умножение.
     * @return Результирующий вектор.
     */
    public Vector4 multiplyMatrixVector(Vector4 point) {
        double[] pointArr = new double[]{point.getX(), point.getY(), point.getZ(), point.getW()};
        double[] result = new double[4];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                result[i] += data[i][j] * pointArr[j];
            }
        }

        return new Vector4(result[0] / result[3], result[1] / result[3], result[2] / result[3], 1.0);
    }

    /**
     * Умножает матрицу на вектор.
     *
     * @param vector Вектор, на который происходит умножение.
     * @return Результирующий вектор.
     */
    public double[] multiplyMatrixVector(double[] vector) {
        double[] result = new double[vector.length];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                result[i] += data[i][j] * vector[j];
            }
        }

        return result;
    }

    /**
     * Умножает матрицу на матрицу.
     *
     * @param matrix Матрица, на которую происходит умножение.
     * @return Результирующая матрица.
     */
    public Matrix multiplyMatrixMatrix(Matrix matrix) {
        double[][] resultData = new double[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                for (int k = 0; k < 4; k++) {
                    resultData[i][j] += data[i][k] * matrix.data[k][j];
                }
            }
        }

        return new Matrix(resultData);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (double[] row : data) {
            for (double element : row) {
                sb.append(element).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}