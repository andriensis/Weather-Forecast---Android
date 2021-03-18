package it.massimilianomassaro.weatherforecast.data.remote.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class DailyWeatherForecast(
    val localDate: LocalDate,
    val hourlyWeatherForecastList: MutableList<HourlyWeatherForecast> = mutableListOf()
): Parcelable