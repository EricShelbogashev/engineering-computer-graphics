package ru.nsu.e.shelbogashev.wireframe;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import ru.nsu.e.shelbogashev.wireframe.bsplineeditor.BSplineEditor;

public class Application {
    public static void main(String[] args) {
        FlatMacLightLaf.setup();
        new BSplineEditor();
    }
}
