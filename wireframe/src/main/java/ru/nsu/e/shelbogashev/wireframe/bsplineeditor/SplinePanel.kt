package ru.nsu.e.shelbogashev.wireframe.bsplineeditor;

import ru.nsu.e.shelbogashev.wireframe.utils.Point2D;
import ru.nsu.e.shelbogashev.wireframe.utils.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 * Панель для редактирования B-сплайнов.
 */
public class SplinePanel extends JPanel implements MouseMotionListener, MouseListener, MouseWheelListener {
    private final BSpline bSpline;
    private final JScrollPane scrollPane = new JScrollPane();
    private int indent = Settings.DEFAULT_INDENT;
    private int curX;
    private int curY;
    private int activePointIndex;
    private Action curAction = Action.NOTHING;
    private int width = 640;
    private int height = 240;

    /**
     * Создает панель для редактирования B-сплайнов с заданными параметрами.
     *
     * @param spline Параметры B-сплайнов.
     */
    public SplinePanel(BSpline spline) {
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);
        bSpline = spline;
        scrollPane.setMaximumSize(new Dimension(width, height));
        scrollPane.setViewportView(this);
        scrollPane.revalidate();
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
    }

    private void drawAxis(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);
        g.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());
        int height = getHeight();
        int width = getWidth();
        int linesNumber = (width / 2) / indent;
        for (int i = 1; i <= linesNumber; i++) {
            g.drawLine(width / 2 - indent * i, height / 2 - 3, width / 2 - indent * i, height / 2 + 3);
            g.drawLine(width / 2 + indent * i, height / 2 - 3, width / 2 + indent * i, height / 2 + 3);
        }
        linesNumber = (height / 2) / indent;
        for (int i = 1; i <= linesNumber; i++) {
            g.drawLine(width / 2 - 3, height / 2 - indent * i, width / 2 + 3, height / 2 - indent * i);
            g.drawLine(width / 2 - 3, height / 2 + indent * i, width / 2 + 3, height / 2 + indent * i);
        }
    }

    private void drawSplineAnchorPoints(Graphics g) {
        g.setColor(Color.GREEN);
        var points = bSpline.getAnchorPoints();
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        for (Point2D p : points) {
            g.drawOval((int) (centerX + p.getX() * indent - Settings.RADIUS / 2), (int) (centerY - p.getY() * indent - Settings.RADIUS / 2), Settings.RADIUS, Settings.RADIUS);
        }
        g.setColor(Color.WHITE);
        drawLinesBetweenPoints(g, points);
    }

    private void drawLinesBetweenPoints(Graphics g, java.util.List<Point2D> points) {
        for (int i = 0; i < points.size() - 1; i++) {
            Point2D p1 = convertToScreen(points.get(i));
            Point2D p2 = convertToScreen(points.get(i + 1));
            g.drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY());
        }
    }

    private void drawSpline(Graphics g) {
        g.setColor(Color.RED);
        var points = bSpline.getSplinePoints();
        if (points == null || points.size() < 4)
            return;
        drawLinesBetweenPoints(g, points);
    }

    private Point2D convertToScreen(Point2D p) {
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        double x = centerX + p.getX() * indent;
        double y = centerY - p.getY() * indent;
        return new Point2D(x, y);
    }

    /**
     * Пересоздает B-сплайн и перерисовывает панель.
     */
    public void recreateSpline() {
        bSpline.createBSpline();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawAxis(g);
        drawSplineAnchorPoints(g);
        drawSpline(g);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        double centerX = (double) getWidth() / 2;
        double centerY = (double) getHeight() / 2;
        switch (curAction) {
            case POINT_MOVING -> {
                var curPoint = bSpline.getAnchorPoints().get(activePointIndex);
                curPoint.setX((e.getX() - centerX) / indent);
                curPoint.setY((centerY - e.getY()) / indent);
                bSpline.createBSpline();
            }
            case CANVAS_MOVING -> {
                int dx = curX - e.getX();
                int dy = curY - e.getY();
                Dimension panelSize = new Dimension(getWidth(), getHeight());
                Point viewPos = scrollPane.getViewport().getViewPosition();
                int maxX = viewPos.x + scrollPane.getViewport().getWidth();
                int maxY = viewPos.y + scrollPane.getViewport().getHeight();
                if (dx < 0 && viewPos.x == 0) {
                    width -= dx;
                    curX -= dx;
                } else if (dx >= 0 && maxX == panelSize.width) {
                    width += dx;
                }
                if (dy < 0 && viewPos.y == 0) {
                    height -= dy;
                    curY -= dy;
                } else if (dy >= 0 && maxY == panelSize.height) {
                    height += dy;
                }
                scrollPane.getHorizontalScrollBar().setValue(viewPos.x + dx);
                scrollPane.getVerticalScrollBar().setValue(viewPos.y + dy);
            }
        }
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        double centerX = (double) getWidth() / 2;
        double centerY = (double) getHeight() / 2;
        if (e.getButton() == MouseEvent.BUTTON1)
            bSpline.addAnchorPoint(new Point2D((e.getX() - centerX) / indent, (centerY - e.getY()) / indent));
        if (e.getButton() == MouseEvent.BUTTON3)
            bSpline.deleteAnchorPoint();
        bSpline.createBSpline();
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        curX = e.getX();
        curY = e.getY();
        checkPoint();
    }

    private void checkPoint() {
        var anchorPoints = bSpline.getAnchorPoints();
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int i = 0;
        for (Point2D p : anchorPoints) {
            double globalX = p.getX() * indent + centerX;
            double globalY = centerY - p.getY() * indent;
            if (curX <= globalX + Settings.RADIUS && curX >= globalX - Settings.RADIUS && curY <= globalY + Settings.RADIUS && curY >= globalY - Settings.RADIUS) {
                activePointIndex = i;
                curAction = Action.POINT_MOVING;
                break;
            }
            i++;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        curAction = Action.NOTHING;
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int modifier = e.getWheelRotation();
        indent -= modifier;
        repaint();
    }

    enum Action {
        POINT_MOVING,
        CANVAS_MOVING,
        NOTHING
    }
}