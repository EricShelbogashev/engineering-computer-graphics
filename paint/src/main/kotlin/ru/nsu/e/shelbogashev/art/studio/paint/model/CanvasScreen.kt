package ru.nsu.e.shelbogashev.art.studio.paint.model

import androidx.compose.ui.graphics.Color
import ru.nsu.e.shelbogashev.art.studio.core.model.Context
import ru.nsu.e.shelbogashev.art.studio.core.model.screens.Screen
import ru.nsu.e.shelbogashev.art.studio.core.model.screens.ScreenController

class CanvasScreen(context: Context) : Screen(context), CanvasScreenController {
    override fun controller(): ScreenController = this

    override fun close() {
        TODO("Not yet implemented")
    }

    override fun requestColor(): RequestColorController {
        TODO("Not yet implemented")
    }

    override fun getColor(): Color {
        TODO("Not yet implemented")
    }
}