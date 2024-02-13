package ru.nsu.e.shelbogashev.wireframe.bsplineeditor;

import ru.nsu.e.shelbogashev.wireframe.wireframe.WireframeFrame;

import javax.swing.*;
import java.awt.*;

/**
 * Класс BSplineEditor представляет графический интерфейс для редактирования B-сплайнов.
 */
public class BSplineEditor extends JFrame {

    /**
     * Создает экземпляр класса BSplineEditor.
     */
    public BSplineEditor() {
        initializeFrame();
        setupComponents();
        setupMenu();
        setVisible(true);
    }

    /**
     * Инициализирует основные параметры окна.
     */
    private void initializeFrame() {
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setMinimumSize(new Dimension(900, 700));
        setBounds(380, 100, 900, 600);
    }

    /**
     * Настроивает компоненты интерфейса.
     */
    private void setupComponents() {
        BSpline bSpline = new BSpline();
        SplinePanel editorPanel = new SplinePanel(bSpline);
        add(editorPanel, BorderLayout.CENTER);

        WireframeFrame wireframeFrame = new WireframeFrame(bSpline);
        OptionsPanel optionsPanel = new OptionsPanel(editorPanel, wireframeFrame);
        add(optionsPanel, BorderLayout.SOUTH);
    }

    /**
     * Настроивает меню приложения.
     */
    private void setupMenu() {
        JMenuBar menu = new JMenuBar();
        add(menu, BorderLayout.NORTH);

        JMenu file = new JMenu("Файл");
        menu.add(file);

        JMenu about = new JMenu("О программе");
        menu.add(about);

        JMenuItem save = new JMenuItem("Сохранить");
        JMenuItem load = new JMenuItem("Загрузить");
        file.add(save);
        file.add(load);

        JMenuItem aboutItem = new JMenuItem("О программе");
        about.add(aboutItem);

        aboutItem.addActionListener(e -> showAboutDialog());
    }

    /**
     * Отображает диалоговое окно с информацией о программе и авторе.
     */
    private void showAboutDialog() {
        JOptionPane.showConfirmDialog(
                null,
                "Автор: Шелбогашев Эрик 21209, ФИТ НГУ",
                "О программе",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE);
    }
}
