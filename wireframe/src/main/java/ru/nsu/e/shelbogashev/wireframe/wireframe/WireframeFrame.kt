package ru.nsu.e.shelbogashev.wireframe.wireframe;

import ru.nsu.e.shelbogashev.wireframe.bsplineeditor.BSpline;

import javax.swing.*;
import java.awt.*;

/**
 * Представляет собой графическое окно для отображения проволочной модели.
 */
public class WireframeFrame extends JFrame {
    private final WireframePanel wireframePanel;

    /**
     * Создает новый экземпляр графического окна для проволочной модели.
     *
     * @param bSpline Кривая Безье, на основе которой будет создана проволочная модель.
     */
    public WireframeFrame(BSpline bSpline) {
        wireframePanel = new WireframePanel(bSpline);
        int width = 900;
        int height = 600;
        setMinimumSize(new Dimension(width, height));

        add(wireframePanel);

        setVisible(true);
    }

    /**
     * Создает проволочную модель и отображает ее на панели.
     */
    public void createWireframe() {
        wireframePanel.createWireframe();
    }

}
