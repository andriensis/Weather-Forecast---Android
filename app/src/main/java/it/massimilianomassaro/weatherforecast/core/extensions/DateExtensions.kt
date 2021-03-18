package it.massimilianomassaro.weatherforecast.core.extensions

import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

fun Date.toFormattedString(): String {
    val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ITALIAN)
    return simpleDateFormat.format(this)
}