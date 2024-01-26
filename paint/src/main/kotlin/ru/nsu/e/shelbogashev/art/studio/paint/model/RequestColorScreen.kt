package ru.nsu.e.shelbogashev.art.studio.paint.model

import androidx.compose.ui.graphics.Color
import ru.nsu.e.shelbogashev.art.studio.core.model.Context
import ru.nsu.e.shelbogashev.art.studio.core.model.screens.Screen

class RequestColorScreen(context: Context) : Screen(context), RequestColorController {
    override fun controller(): RequestColorController = this

    override fun close() {
        TODO("Not yet implemented")
    }

    override fun setColor(color: Color) {
        TODO("Not yet implemented")
    }
}