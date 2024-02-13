package ru.nsu.e.shelbogashev.wireframe.bsplineeditor

import ru.nsu.e.shelbogashev.wireframe.utils.Matrix
import ru.nsu.e.shelbogashev.wireframe.utils.Point2D
import ru.nsu.e.shelbogashev.wireframe.utils.Settings

/**
 * Класс для работы с B-сплайнами.
 */
class BSpline {
    private val splineMatrix = Matrix(
        arrayOf(
            doubleArrayOf(-1.0 / 6, 3.0 / 6, -3.0 / 6, 1.0 / 6),
            doubleArrayOf(3.0 / 6, -6.0 / 6, 3.0 / 6, 0.0),
            doubleArrayOf(-3.0 / 6, 0.0, 3.0 / 6, 0.0),
            doubleArrayOf(1.0 / 6, 4.0 / 6, 1.0 / 6, 0.0)
        )
    )

    /**
     * Точки, которые будут использоваться для приближения B-сплайна.
     */
    private val anchorPoints: MutableList<Point2D> = ArrayList()

    /**
     * Точки, которые будут аппроксимировать часть B-сплайна.
     */
    var splinePoints: ArrayList<Point2D>? = null
        private set

    /**
     * Добавляет якорную точку для аппроксимации B-сплайна.
     *
     * @param point Точка, которую необходимо добавить в качестве якорной точки.
     */
    fun addAnchorPoint(point: Point2D) {
        anchorPoints.add(point)
        createBSpline()
    }

    /**
     * Удаляет последнюю добавленную якорную точку.
     */
    fun deleteAnchorPoint() {
        if (anchorPoints.isNotEmpty()) {
            anchorPoints.removeAt(anchorPoints.size - 1)
            createBSpline()
        }
    }

    /**
     * Создает B-сплайн на основе заданных якорных точек.
     */
    fun createBSpline() {
        splinePoints = ArrayList() // каждый раз создаем новый массив точек
        val anchorPointsNum = anchorPoints.size

        val step = 1.0 / Settings.SEGMENTS_NUM // шаг между каждой частью сегмента [0, 1]
        var x: Double
        var y: Double

        if (anchorPointsNum < 4) return

        for (i in 1 until anchorPointsNum - 2) {
            val xCoords = doubleArrayOf(
                anchorPoints[i - 1].x,
                anchorPoints[i].x,
                anchorPoints[i + 1].x,
                anchorPoints[i + 2].x
            )

            val yCoords = doubleArrayOf(
                anchorPoints[i - 1].y,
                anchorPoints[i].y,
                anchorPoints[i + 1].y,
                anchorPoints[i + 2].y
            )

            val xVector = splineMatrix.multiplyMatrixVector(xCoords)
            val yVector = splineMatrix.multiplyMatrixVector(yCoords)

            var t: Double
            for (k in 0..Settings.SEGMENTS_NUM) {
                t = k * step
                x = t * t * t * xVector[0] + t * t * xVector[1] + t * xVector[2] + xVector[3]
                y = t * t * t * yVector[0] + t * t * yVector[1] + t * yVector[2] + yVector[3]

                splinePoints!!.add(Point2D(x, y))
            }
        }
    }

    /**
     * Возвращает список якорных точек.
     *
     * @return Список якорных точек.
     */
    fun getAnchorPoints(): List<Point2D> {
        return this.anchorPoints
    }
}