package ru.nsu.e.shelbogashev.art.studio.paint

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ru.nsu.e.shelbogashev.art.studio.core.StudioApp
import ru.nsu.e.shelbogashev.art.studio.core.model.screens.Screen
import ru.nsu.e.shelbogashev.art.studio.core.model.screens.ScreenAction
import ru.nsu.e.shelbogashev.art.studio.paint.model.CanvasScreen
import ru.nsu.e.shelbogashev.art.studio.paint.view.CanvasView
import ru.nsu.e.shelbogashev.art.studio.paint.view.Line

@Composable
@Preview
fun Paint() {
    val lines = remember { mutableStateListOf<Line>() }
    val app = remember {
        StudioApp.Builder()
            .splash(::CanvasScreen)
            .onScreenStateChanged(::handleScreenStateChanges)
            .build()
    }

    MaterialTheme {
        CanvasView(Modifier.fillMaxSize(), lines)
    }
}

fun handleScreenStateChanges(screen: Screen, action: ScreenAction) {

}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        Paint()
    }
}
