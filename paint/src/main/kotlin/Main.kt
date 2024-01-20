import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ru.nsu.e.shelbogashev.art.studio.core.StudioApp
import ru.nsu.e.shelbogashev.art.studio.core.model.screens.Screen
import ru.nsu.e.shelbogashev.art.studio.core.model.screens.ScreenController

@Composable
@Preview
fun App() {
    var text by remember { mutableStateOf("Hello, World!") }

    MaterialTheme {
        Button(onClick = {
            text = "Hello, Desktop!"
            val screen = object : Screen {
                override fun controller(): ScreenController = object : ScreenController {}
            }
            val app = StudioApp.Builder()
                .splash(screen)
                .onScreenStateChanged { screen, action ->
                    println(screen)
                    screen.controller()
                }
                .build()

            app.run()
        }) {
            Text(text)
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
