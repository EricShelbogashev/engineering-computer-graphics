package ru.nsu.e.shelbogashev.wireframe.wireframe;

import org.jetbrains.annotations.NotNull;
import ru.nsu.e.shelbogashev.wireframe.utils.Matrix;
import ru.nsu.e.shelbogashev.wireframe.utils.Point2D;
import ru.nsu.e.shelbogashev.wireframe.utils.Settings;
import ru.nsu.e.shelbogashev.wireframe.utils.Vector4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Представляет собой проволочную модель (wireframe) для отображения объектов в трехмерном пространстве.
 */
public class Wireframe {
    private Matrix rotationMatrix;
    private Matrix cameraTranslateMatrix;
    private Matrix cameraPerspectiveMatrix;
    private Matrix translateMatrix;
    private Matrix normalizeMatrix;
    private List<Vector4> wireframePoints;
    private List<Integer> edges;

    /**
     * Создает новую проволочную модель с начальными матрицами преобразований.
     */
    public Wireframe() {
        this.rotationMatrix = new Matrix(new double[][]{
                {1.0, 0, 0, 0},
                {0, 1.0, 0, 0},
                {0, 0, 1.0, 0},
                {0, 0, 0, 1.0}
        });
        setTranslateMatrix();
        setCameraTranslateMatrix();
        setCameraPerspectiveMatrix();
    }

    /**
     * Устанавливает матрицу поворота вокруг оси.
     *
     * @param p1 Первая точка, задающая ось вращения.
     * @param p2 Вторая точка, задающая ось вращения.
     */
    public void setRotationMatrix(java.awt.Point p1, java.awt.Point p2) {
        Vector4 axis = new Vector4(-(p2.y - p1.y), -(p2.x - p1.x), 0);
        if (axis.getX() == 0 && axis.getY() == 0)
            return;
        axis.normalize();
        double rotationAngle = 5.0;
        double cos = Math.cos(Math.toRadians(rotationAngle));
        Matrix rotation = getMatrix(rotationAngle, axis, cos);
        this.translateMatrix = rotation.multiplyMatrixMatrix(translateMatrix);
        rotationMatrix = rotation.multiplyMatrixMatrix(rotationMatrix);
    }

    @NotNull
    private static Matrix getMatrix(double rotationAngle, Vector4 axis, double cos) {
        double sin = Math.sin(Math.toRadians(rotationAngle));
        double x = axis.getX(), y = axis.getY(), z = axis.getZ();
        return new Matrix(new double[][]{
                {cos + (1 - cos) * x * x, (1 - cos) * x * y - sin * z, (1 - cos) * x * z + sin * y, 0},
                {(1 - cos) * y * x + sin * z, cos + (1 - cos) * y * y, (1 - cos) * y * z - sin * x, 0},
                {(1 - cos) * z * x - sin * y, (1 - cos) * z * y + sin * x, cos + (1 - cos) * z * z, 0},
                {0, 0, 0, 1.0}
        });
    }

    /**
     * Устанавливает матрицу переноса камеры.
     */
    public void setCameraTranslateMatrix() {
        this.cameraTranslateMatrix = new Matrix(new double[][]{
                {1.0, 0, 0, 0},
                {0, 1.0, 0, 0},
                {0, 0, 1.0, 10.0},
                {0, 0, 0, 1.0}
        });
    }

    /**
     * Устанавливает матрицу переноса.
     */
    public void setTranslateMatrix() {
        this.translateMatrix = new Matrix(new double[][]{
                {1.0, 0, 0, 0},
                {0, 1.0, 0, 0},
                {0, 0, 1.0, 0},
                {0, 0, 0, 1.0}
        });
    }

    /**
     * Устанавливает матрицу перспективной проекции камеры.
     */
    public void setCameraPerspectiveMatrix() {
        double nearClip = 5.0;
        cameraPerspectiveMatrix = new Matrix(new double[][]{
                {2 * nearClip, 0, 0, 0},
                {0, 2 * nearClip, 0, 0},
                {0, 0, 1.0, 0},
                {0, 0, 1.0, 0}
        });
    }

    private void setNormalizeMatrix() {
        double maxX = wireframePoints.getFirst().getX(), minX = wireframePoints.getFirst().getX();
        double maxY = wireframePoints.getFirst().getY(), minY = wireframePoints.getFirst().getY();
        double maxZ = wireframePoints.getFirst().getZ(), minZ = wireframePoints.getFirst().getZ();
        for (Vector4 v : wireframePoints) {
            if (v.getX() > maxX)
                maxX = v.getX();
            if (v.getY() > maxY)
                maxY = v.getY();
            if (v.getZ() > maxZ)
                maxZ = v.getZ();
            if (v.getX() < minX)
                minX = v.getX();
            if (v.getY() < minY)
                minY = v.getY();
            if (v.getZ() < minZ)
                minZ = v.getZ();
        }
        double distX = maxX - minX;
        double distY = maxY - minY;
        double distZ = maxZ - minZ;
        double res = Math.max(distX, Math.max(distY, distZ));
        if (res == 0.0)
            res = 1.0;
        normalizeMatrix = new Matrix(new double[][]{
                {1.0 / res, 0, 0, 0},
                {0, 1.0 / res, 0, 0},
                {0, 0, 1.0 / res, 0},
                {0, 0, 0, 1.0}
        });
    }

    /**
     * Создает проволочную модель на основе точек кривой Безье.
     *
     * @param bSplinePoints Точки кривой Безье.
     */
    public void createWireframePoints(List<Point2D> bSplinePoints) {
        wireframePoints = new ArrayList<>();
        edges = new ArrayList<>();
        double angle = (double) 360 / (Settings.getGeneratrixNum() * Settings.getCirclesAccuracy());
        double curAngle, cos, sin;
        for (int i = 0; i < Settings.getGeneratrixNum() * Settings.getCirclesAccuracy(); i++) {
            curAngle = i * angle;
            cos = Math.cos(Math.toRadians(curAngle));
            sin = Math.sin(Math.toRadians(curAngle));
            for (Point2D p : bSplinePoints) {
                Vector4 newPoint = new Vector4(p.getY() * cos, p.getY() * sin, p.getX());
                Vector4 planePoint = translateMatrix.multiplyMatrixVector(newPoint);
                planePoint = cameraTranslateMatrix.multiplyMatrixVector(planePoint);
                wireframePoints.add(planePoint);
            }
        }
        setNormalizeMatrix();
        wireframePoints.replaceAll(point -> normalizeMatrix.multiplyMatrixVector(point));
        int N = bSplinePoints.size();
        for (int i = 0; i < Settings.getGeneratrixNum() * Settings.getCirclesAccuracy(); i += Settings.getCirclesAccuracy()) {
            for (int j = 0; j < N - 1; j++) {
                edges.add(j + i * N);
                edges.add(j + 1 + i * N);
            }
        }
        for (int i = 0; i < Settings.getGeneratrixNum() * Settings.getCirclesAccuracy(); i++) {
            edges.add(i * N);
            edges.add(((i + 1) % (Settings.getGeneratrixNum() * Settings.getCirclesAccuracy())) * N);
        }
        for (int i = 0; i < Settings.getGeneratrixNum() * Settings.getCirclesAccuracy(); i++) {
            edges.add(i * N + N - 1);
            edges.add(((i + 1) % (Settings.getGeneratrixNum() * Settings.getCirclesAccuracy())) * N + N - 1);
        }
        if (Settings.getCirclesNum() > 2) {
            int step = N / (Settings.getCirclesNum() - 1);
            for (int i = 1; i <= Settings.getCirclesNum() - 2; i++) {
                for (int j = 0; j < Settings.getGeneratrixNum() * Settings.getCirclesAccuracy(); j++) {
                    edges.add(j * N + i * step);
                    edges.add(((j + 1) % (Settings.getGeneratrixNum() * Settings.getCirclesAccuracy())) * N + i * step);
                }
            }
        }
    }

    /**
     * Возвращает куб в виде проволочной модели.
     *
     * @return Пара, содержащая вершины куба и его ребра.
     */
    public Map<Integer, List<Point2D>> getCube() {
        List<Vector4> cubePoints = new ArrayList<>();
        cubePoints.add(new Vector4(1.0, 1.0, 1.0));
        cubePoints.add(new Vector4(1.0, 1.0, -1.0));
        cubePoints.add(new Vector4(1.0, -1.0, 1.0));
        cubePoints.add(new Vector4(1.0, -1.0, -1.0));
        cubePoints.add(new Vector4(-1.0, 1.0, 1.0));
        cubePoints.add(new Vector4(-1.0, 1.0, -1.0));
        cubePoints.add(new Vector4(-1.0, -1.0, 1.0));
        cubePoints.add(new Vector4(-1.0, -1.0, -1.0));
        List<Point2D> wireframePoints = new ArrayList<>();
        for (Vector4 v : cubePoints) {
            Vector4 planePoint;
            planePoint = translateMatrix.multiplyMatrixVector(v);
            planePoint = cameraTranslateMatrix.multiplyMatrixVector(planePoint);
            planePoint = cameraPerspectiveMatrix.multiplyMatrixVector(planePoint);
            wireframePoints.add(new Point2D(planePoint.getX(), planePoint.getY()));
        }
        List<Point2D> edges = new ArrayList<>();
        for (int i = 0; i < cubePoints.size(); i++) {
            for (int j = i + 1; j < cubePoints.size(); j++) {
                int diff = 0;
                if (Math.abs(cubePoints.get(i).getX() - cubePoints.get(j).getX()) == 2)
                    diff++;
                if (Math.abs(cubePoints.get(i).getY() - cubePoints.get(j).getY()) == 2)
                    diff++;
                if (Math.abs(cubePoints.get(i).getZ() - cubePoints.get(j).getZ()) == 2)
                    diff++;
                if (diff == 1)
                    edges.add(new Point2D(i, j));
            }
        }
        Map<Integer, List<Point2D>> cube = new HashMap<>();
        cube.put(0, wireframePoints);
        cube.put(1, edges);
        return cube;
    }

    /**
     * Возвращает список точек проволочной модели.
     *
     * @return Список точек проволочной модели.
     */
    public List<Vector4> getWireframePoints() {
        return this.wireframePoints;
    }

    /**
     * Возвращает список ребер проволочной модели.
     *
     * @return Список ребер проволочной модели.
     */
    public List<Integer> getEdges() {
        return this.edges;
    }
}