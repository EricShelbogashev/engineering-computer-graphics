package ru.nsu.e.shelbogashev.art.studio.paint.support

import java.util.*

object StringResource {

    fun loadString(key: String, locale: Locale): String {
        val bundle = ResourceBundle.getBundle("strings", locale)
        return bundle.getString(key)
    }
}
