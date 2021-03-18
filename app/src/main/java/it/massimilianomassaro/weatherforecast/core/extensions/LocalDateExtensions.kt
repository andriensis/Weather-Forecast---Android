package it.massimilianomassaro.weatherforecast.core.extensions

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun LocalDate.toFormattedString(): String {
    return this.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
}