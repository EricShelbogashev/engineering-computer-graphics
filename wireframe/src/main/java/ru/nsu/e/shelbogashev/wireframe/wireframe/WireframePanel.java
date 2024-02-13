package ru.nsu.e.shelbogashev.wireframe.wireframe;

import ru.nsu.e.shelbogashev.wireframe.bsplineeditor.BSpline;
import ru.nsu.e.shelbogashev.wireframe.utils.Point2D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 * Представляет панель для отображения проволочной модели.
 */
public class WireframePanel extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {
    private final Wireframe wireframe;
    private final BSpline bSpline;

    private Point prevPoint;

    /**
     * Создает новую панель для отображения проволочной модели на основе заданной кривой Безье.
     *
     * @param spline Кривая Безье, на основе которой будет создана проволочная модель.
     */
    public WireframePanel(BSpline spline) {
        this.bSpline = spline;
        this.wireframe = new Wireframe();
        setBackground(Color.WHITE);

        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(2));

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        if (bSpline.getAnchorPoints().size() < 4 || bSpline.getSplinePoints() == null) {
            var cube = wireframe.getCube();
            var cubePoints = cube.get(0);
            var cubeEdges = cube.get(1);

            for (Point2D e : cubeEdges) {
                g2d.drawLine(
                        (int) (centerX + cubePoints.get((int) e.getX()).getX() * 200), (int) (centerY - cubePoints.get((int) e.getX()).getY() * 200),
                        (int) (centerX + cubePoints.get((int) e.getY()).getX() * 200), (int) (centerY - cubePoints.get((int) e.getY()).getY() * 200)
                );
            }
        } else {
            wireframe.createWireframePoints(bSpline.getSplinePoints());
            var points = wireframe.getWireframePoints();
            var edges = wireframe.getEdges();

            g2d.setColor(Color.BLACK);
            for (int i = 0; i < edges.size(); i += 2) {
                var p1 = points.get(edges.get(i));
                var p2 = points.get(edges.get(i + 1));

                g2d.drawLine(
                        (int) (centerX + p1.getX() * 200), (int) (centerY - p1.getY() * 200),
                        (int) (centerX + p2.getX() * 200), (int) (centerY - p2.getY() * 200)
                );
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        prevPoint = e.getPoint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        wireframe.setRotationMatrix(prevPoint, e.getPoint());
        prevPoint = e.getPoint();

        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        repaint();
    }

    /**
     * Создает проволочную модель и отображает ее на панели.
     */
    public void createWireframe() {
        repaint();
    }
}
