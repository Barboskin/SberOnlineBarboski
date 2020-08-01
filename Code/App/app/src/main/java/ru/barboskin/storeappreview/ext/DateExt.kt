package ru.barboskin.storeappreview.ext

import java.text.SimpleDateFormat
import java.util.*

private val dateFormat = SimpleDateFormat("d MMMM y", Locale.getDefault())

fun Date.formatAsString(): String {
    return dateFormat.format(this)
}
