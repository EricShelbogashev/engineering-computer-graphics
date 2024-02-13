package ru.nsu.e.shelbogashev.wireframe.bsplineeditor;

import ru.nsu.e.shelbogashev.wireframe.utils.Settings;
import ru.nsu.e.shelbogashev.wireframe.wireframe.WireframeFrame;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 * Панель опций предоставляет пользователю интерфейс для настройки параметров приложения.
 */
public class OptionsPanel extends JPanel {
    private final WireframeFrame wireframeFrame;

    /**
     * Создает панель опций с заданными компонентами.
     *
     * @param splinePanel    Панель для редактирования B-сплайнов.
     * @param wireframeFrame Окно для отображения проволочной модели.
     */
    public OptionsPanel(SplinePanel splinePanel, WireframeFrame wireframeFrame) {
        this.wireframeFrame = wireframeFrame;
        setupPanel();
        addApplyButton();
        addSpinnerWithLabel("Генератрисы", Settings.getGeneratrixNum(), 2, 10, 1, e ->
                Settings.setGeneratrixNum((int) ((JSpinner) e.getSource()).getValue()));
        addSpinnerWithLabel("Круги", Settings.getCirclesNum(), 2, 10, 1, e ->
                Settings.setCirclesNum((int) ((JSpinner) e.getSource()).getValue()));
        addSpinnerWithLabel("Точность", Settings.getCirclesAccuracy(), 1, 10, 1, e ->
                Settings.setCirclesAccuracy((int) ((JSpinner) e.getSource()).getValue()));
        addSpinnerWithLabel("Сегменты", Settings.SEGMENTS_NUM, 1, 20, 1, e -> {
            Settings.SEGMENTS_NUM = (int) ((JSpinner) e.getSource()).getValue();
            splinePanel.recreateSpline();
        });
    }

    /**
     * Настройка внешнего вида панели опций.
     */
    private void setupPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setPreferredSize(new Dimension(640, 200));
    }

    /**
     * Добавляет кнопку применения изменений.
     */
    private void addApplyButton() {
        JButton applyButton = new JButton("Применить");
        applyButton.addActionListener(e -> wireframeFrame.createWireframe());
        add(applyButton, BorderLayout.SOUTH);
    }

    /**
     * Добавляет спиннер со значением и меткой.
     *
     * @param labelText      Текст метки.
     * @param initialValue   Начальное значение спиннера.
     * @param min            Минимальное значение спиннера.
     * @param max            Максимальное значение спиннера.
     * @param step           Шаг изменения значения спиннера.
     * @param changeListener Обработчик изменения значения спиннера.
     */
    private void addSpinnerWithLabel(String labelText, int initialValue, int min, int max, int step,
                                     ChangeListener changeListener) {
        JLabel label = new JLabel(labelText);
        add(label);

        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(initialValue, min, max, step);
        JSpinner spinner = new JSpinner(spinnerModel);
        spinner.addChangeListener(changeListener);
        add(spinner);
    }
}