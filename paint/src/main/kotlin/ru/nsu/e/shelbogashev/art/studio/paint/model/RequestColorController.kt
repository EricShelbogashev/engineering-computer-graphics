package ru.nsu.e.shelbogashev.art.studio.paint.model

import androidx.compose.ui.graphics.Color
import ru.nsu.e.shelbogashev.art.studio.core.model.screens.ScreenController

interface RequestColorController : ScreenController {
    fun setColor(color: Color)
}