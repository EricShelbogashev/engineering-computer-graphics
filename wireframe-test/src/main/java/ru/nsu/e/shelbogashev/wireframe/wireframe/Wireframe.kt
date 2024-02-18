package ru.nsu.e.shelbogashev.wireframe.wireframe

import ru.nsu.e.shelbogashev.wireframe.utils.Matrix
import ru.nsu.e.shelbogashev.wireframe.utils.Point2D
import ru.nsu.e.shelbogashev.wireframe.utils.Settings
import ru.nsu.e.shelbogashev.wireframe.utils.Vector4
import java.awt.Point
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.sin

/**
 * Представляет собой проволочную модель (wireframe) для отображения объектов в трехмерном пространстве.
 */
class Wireframe {
    private var rotationMatrix: Matrix
    private var cameraTranslateMatrix: Matrix? = null
    private var cameraPerspectiveMatrix: Matrix? = null
    private var translateMatrix: Matrix? = null
    private var normalizeMatrix: Matrix? = null
    var wireframePoints: MutableList<Vector4>? = null
    var edges: MutableList<Int>? = null

    /**
     * Создает новую проволочную модель с начальными матрицами преобразований.
     */
    init {
        this.rotationMatrix = Matrix(
            arrayOf(
                doubleArrayOf(1.0, 0.0, 0.0, 0.0),
                doubleArrayOf(0.0, 1.0, 0.0, 0.0),
                doubleArrayOf(0.0, 0.0, 1.0, 0.0),
                doubleArrayOf(0.0, 0.0, 0.0, 1.0)
            )
        )
        setTranslateMatrix()
        setCameraTranslateMatrix()
        setCameraPerspectiveMatrix()
    }

    /**
     * Устанавливает матрицу поворота вокруг оси.
     *
     * @param p1 Первая точка, задающая ось вращения.
     * @param p2 Вторая точка, задающая ось вращения.
     */
    fun setRotationMatrix(p1: Point, p2: Point) {
        val axis = Vector4(-(p2.y - p1.y).toDouble(), -(p2.x - p1.x).toDouble(), 0.0)
        if (axis.x == 0.0 && axis.y == 0.0) return
        axis.normalize()
        val rotationAngle = 5.0
        val cos = cos(Math.toRadians(rotationAngle))
        val rotation = getMatrix(rotationAngle, axis, cos)
        this.translateMatrix = rotation.multiplyMatrixMatrix(translateMatrix!!)
        rotationMatrix = rotation.multiplyMatrixMatrix(rotationMatrix)
    }

    /**
     * Устанавливает матрицу переноса камеры.
     */
    fun setCameraTranslateMatrix() {
        this.cameraTranslateMatrix = Matrix(
            arrayOf(
                doubleArrayOf(1.0, 0.0, 0.0, 0.0),
                doubleArrayOf(0.0, 1.0, 0.0, 0.0),
                doubleArrayOf(0.0, 0.0, 1.0, 10.0),
                doubleArrayOf(0.0, 0.0, 0.0, 1.0)
            )
        )
    }

    /**
     * Устанавливает матрицу переноса.
     */
    private fun setTranslateMatrix() {
        this.translateMatrix = Matrix(
            arrayOf(
                doubleArrayOf(1.0, 0.0, 0.0, 0.0),
                doubleArrayOf(0.0, 1.0, 0.0, 0.0),
                doubleArrayOf(0.0, 0.0, 1.0, 0.0),
                doubleArrayOf(0.0, 0.0, 0.0, 1.0)
            )
        )
    }

    /**
     * Устанавливает матрицу перспективной проекции камеры.
     */
    private fun setCameraPerspectiveMatrix() {
        val nearClip = 5.0
        cameraPerspectiveMatrix = Matrix(
            arrayOf(
                doubleArrayOf(2 * nearClip, 0.0, 0.0, 0.0),
                doubleArrayOf(0.0, 2 * nearClip, 0.0, 0.0),
                doubleArrayOf(0.0, 0.0, 1.0, 0.0),
                doubleArrayOf(0.0, 0.0, 1.0, 0.0)
            )
        )
    }

    private fun setNormalizeMatrix() {
        var maxX = wireframePoints!!.first().x
        var minX = wireframePoints!!.first().x
        var maxY = wireframePoints!!.first().y
        var minY = wireframePoints!!.first().y
        var maxZ = wireframePoints!!.first().z
        var minZ = wireframePoints!!.first().z
        for (v in wireframePoints!!) {
            if (v.x > maxX) maxX = v.x
            if (v.y > maxY) maxY = v.y
            if (v.z > maxZ) maxZ = v.z
            if (v.x < minX) minX = v.x
            if (v.y < minY) minY = v.y
            if (v.z < minZ) minZ = v.z
        }
        val distX = maxX - minX
        val distY = maxY - minY
        val distZ = maxZ - minZ
        var res = max(distX, max(distY, distZ))
        if (res == 0.0) res = 1.0
        normalizeMatrix = Matrix(
            arrayOf(
                doubleArrayOf(1.0 / res, 0.0, 0.0, 0.0),
                doubleArrayOf(0.0, 1.0 / res, 0.0, 0.0),
                doubleArrayOf(0.0, 0.0, 1.0 / res, 0.0),
                doubleArrayOf(0.0, 0.0, 0.0, 1.0)
            )
        )
    }

    /**
     * Создает проволочную модель на основе точек кривой Безье.
     *
     * @param bSplinePoints Точки кривой Безье.
     */
    fun createWireframePoints(bSplinePoints: List<Point2D>) {
        wireframePoints = mutableListOf()
        edges = mutableListOf()
        val angle = 360.0 / (Settings.generatrixNum * Settings.circlesAccuracy)
        var curAngle: Double
        var cos: Double
        var sin: Double
        for (i in 0 until Settings.generatrixNum * Settings.circlesAccuracy) {
            curAngle = i * angle
            cos = cos(Math.toRadians(curAngle))
            sin = sin(Math.toRadians(curAngle))
            for (p in bSplinePoints) {
                val newPoint = Vector4(p.y * cos, p.y * sin, p.x)
                var planePoint = translateMatrix!!.multiplyMatrixVector(newPoint)
                planePoint = cameraTranslateMatrix!!.multiplyMatrixVector(planePoint)
                wireframePoints!!.add(planePoint)
            }
        }
        setNormalizeMatrix()
        wireframePoints!!.replaceAll { point: Vector4? ->
            normalizeMatrix!!.multiplyMatrixVector(
                point!!
            )
        }
        val N = bSplinePoints.size
        run {
            var i = 0
            while (i < Settings.generatrixNum * Settings.circlesAccuracy) {
                for (j in 0 until N - 1) {
                    edges!!.add(j + i * N)
                    edges!!.add(j + 1 + i * N)
                }
                i += Settings.circlesAccuracy
            }
        }
        for (i in 0 until Settings.generatrixNum * Settings.circlesAccuracy) {
            edges!!.add(i * N)
            edges!!.add(((i + 1) % (Settings.generatrixNum * Settings.circlesAccuracy)) * N)
        }
        for (i in 0 until Settings.generatrixNum * Settings.circlesAccuracy) {
            edges!!.add(i * N + N - 1)
            edges!!.add(((i + 1) % (Settings.generatrixNum * Settings.circlesAccuracy)) * N + N - 1)
        }
        if (Settings.circlesNum > 2) {
            val step = N / (Settings.circlesNum - 1)
            for (i in 1..Settings.circlesNum - 2) {
                for (j in 0 until Settings.generatrixNum * Settings.circlesAccuracy) {
                    edges!!.add(j * N + i * step)
                    edges!!.add(((j + 1) % (Settings.generatrixNum * Settings.circlesAccuracy)) * N + i * step)
                }
            }
        }
    }

    val cube: Map<Int, List<Point2D>>
        /**
         * Возвращает куб в виде проволочной модели.
         *
         * @return Пара, содержащая вершины куба и его ребра.
         */
        get() {
            val cubePoints: MutableList<Vector4> = ArrayList()
            cubePoints.add(Vector4(1.0, 1.0, 1.0))
            cubePoints.add(Vector4(1.0, 1.0, -1.0))
            cubePoints.add(Vector4(1.0, -1.0, 1.0))
            cubePoints.add(Vector4(1.0, -1.0, -1.0))
            cubePoints.add(Vector4(-1.0, 1.0, 1.0))
            cubePoints.add(Vector4(-1.0, 1.0, -1.0))
            cubePoints.add(Vector4(-1.0, -1.0, 1.0))
            cubePoints.add(Vector4(-1.0, -1.0, -1.0))
            val wireframePoints: MutableList<Point2D> = ArrayList()
            for (v in cubePoints) {
                var planePoint = translateMatrix!!.multiplyMatrixVector(v)
                planePoint = cameraTranslateMatrix!!.multiplyMatrixVector(planePoint)
                planePoint = cameraPerspectiveMatrix!!.multiplyMatrixVector(planePoint)
                wireframePoints.add(Point2D(planePoint.x, planePoint.y))
            }
            val edges: MutableList<Point2D> = ArrayList()
            for (i in cubePoints.indices) {
                for (j in i + 1 until cubePoints.size) {
                    var diff = 0
                    if (abs(cubePoints[i].x - cubePoints[j].x).toInt() == 2) diff++
                    if (abs(cubePoints[i].y - cubePoints[j].y).toInt() == 2) diff++
                    if (abs(cubePoints[i].z - cubePoints[j].z).toInt() == 2) diff++
                    if (diff == 1) edges.add(Point2D(i.toDouble(), j.toDouble()))
                }
            }
            val cube: MutableMap<Int, List<Point2D>> = HashMap()
            cube[0] = wireframePoints
            cube[1] = edges
            return cube
        }

    companion object {
        private fun getMatrix(rotationAngle: Double, axis: Vector4, cos: Double): Matrix {
            val sin = sin(Math.toRadians(rotationAngle))
            val x = axis.x
            val y = axis.y
            val z = axis.z
            return Matrix(
                arrayOf(
                    doubleArrayOf(
                        cos + (1 - cos) * x * x,
                        (1 - cos) * x * y - sin * z,
                        (1 - cos) * x * z + sin * y,
                        0.0
                    ),
                    doubleArrayOf(
                        (1 - cos) * y * x + sin * z,
                        cos + (1 - cos) * y * y,
                        (1 - cos) * y * z - sin * x,
                        0.0
                    ),
                    doubleArrayOf(
                        (1 - cos) * z * x - sin * y,
                        (1 - cos) * z * y + sin * x,
                        cos + (1 - cos) * z * z,
                        0.0
                    ),
                    doubleArrayOf(0.0, 0.0, 0.0, 1.0)
                )
            )
        }
    }
}