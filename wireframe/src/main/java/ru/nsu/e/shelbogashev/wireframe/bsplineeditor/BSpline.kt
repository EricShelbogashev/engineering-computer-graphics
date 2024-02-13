package ru.nsu.e.shelbogashev.wireframe.bsplineeditor;

import ru.nsu.e.shelbogashev.wireframe.utils.Matrix;
import ru.nsu.e.shelbogashev.wireframe.utils.Point2D;
import ru.nsu.e.shelbogashev.wireframe.utils.Settings;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс для работы с B-сплайнами.
 */
public class BSpline {
    private final Matrix splineMatrix;

    /**
     * Точки, которые будут использоваться для приближения B-сплайна.
     */
    private final List<Point2D> anchorPoints = new ArrayList<>();

    /**
     * Точки, которые будут аппроксимировать часть B-сплайна.
     */
    private ArrayList<Point2D> splinePoints;

    /**
     * Создает экземпляр класса BSpline и инициализирует матрицу сплайна.
     */
    public BSpline() {
        splineMatrix = new Matrix(new double[][]{
                {-1.0 / 6, 3.0 / 6, -3.0 / 6, 1.0 / 6},
                {3.0 / 6, -6.0 / 6, 3.0 / 6, 0},
                {-3.0 / 6, 0, 3.0 / 6, 0},
                {1.0 / 6, 4.0 / 6, 1.0 / 6, 0}
        });
    }

    /**
     * Добавляет якорную точку для аппроксимации B-сплайна.
     *
     * @param point Точка, которую необходимо добавить в качестве якорной точки.
     */
    public void addAnchorPoint(Point2D point) {
        anchorPoints.add(point);
        createBSpline();
    }

    /**
     * Удаляет последнюю добавленную якорную точку.
     */
    public void deleteAnchorPoint() {
        if (!anchorPoints.isEmpty()) {
            anchorPoints.remove(anchorPoints.size() - 1);
            createBSpline();
        }
    }

    /**
     * Создает B-сплайн на основе заданных якорных точек.
     */
    public void createBSpline() {
        splinePoints = new ArrayList<>(); // каждый раз создаем новый массив точек
        int anchorPointsNum = anchorPoints.size();

        double step = (double) 1 / Settings.SEGMENTS_NUM; // шаг между каждой частью сегмента [0, 1]
        double x, y;

        if (anchorPointsNum < 4)
            return;

        for (int i = 1; i < anchorPointsNum - 2; i++) {
            double[] xCoords = {
                    anchorPoints.get(i - 1).getX(),
                    anchorPoints.get(i).getX(),
                    anchorPoints.get(i + 1).getX(),
                    anchorPoints.get(i + 2).getX()
            };

            double[] yCoords = {
                    anchorPoints.get(i - 1).getY(),
                    anchorPoints.get(i).getY(),
                    anchorPoints.get(i + 1).getY(),
                    anchorPoints.get(i + 2).getY()
            };

            double[] xVector = splineMatrix.multiplyMatrixVector(xCoords);
            double[] yVector = splineMatrix.multiplyMatrixVector(yCoords);

            double t;
            for (int k = 0; k <= Settings.SEGMENTS_NUM; k++) {
                t = k * step;
                x = t * t * t * xVector[0] + t * t * xVector[1] + t * xVector[2] + xVector[3];
                y = t * t * t * yVector[0] + t * t * yVector[1] + t * yVector[2] + yVector[3];

                splinePoints.add(new Point2D(x, y));
            }
        }
    }

    /**
     * Возвращает список якорных точек.
     *
     * @return Список якорных точек.
     */
    public List<Point2D> getAnchorPoints() {
        return this.anchorPoints;
    }

    /**
     * Возвращает список точек, аппроксимирующих B-сплайн.
     *
     * @return Список точек B-сплайна.
     */
    public ArrayList<Point2D> getSplinePoints() {
        return this.splinePoints;
    }
}