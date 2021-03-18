package it.massimilianomassaro.weatherforecast.core.extensions

import java.text.DecimalFormat

fun Double.formatNoDigits(): String {
    return DecimalFormat("##").format(this)
}