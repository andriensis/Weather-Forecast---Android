package it.massimilianomassaro.weatherforecast.data.remote.model

import android.os.Parcelable
import it.massimilianomassaro.weatherforecast.BuildConfig
import it.massimilianomassaro.weatherforecast.core.extensions.toFormattedString

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class HourlyWeatherForecast(
    val dt: Long,
    val main: WeatherDataMain,
    val weather: List<WeatherData>
): Parcelable

@Parcelize
data class WeatherDataMain(
    val temp: Double
): Parcelable

@Parcelize
data class WeatherData(
    val description: String,
    val icon: String
): Parcelable

fun List<HourlyWeatherForecast>.toDailyWeatherForecastList(): List<DailyWeatherForecast> {
    val list = mutableListOf<DailyWeatherForecast>()
    this.forEach {
        val localDate = Instant.ofEpochMilli(it.dt*1000).atZone(ZoneId.systemDefault()).toLocalDate()
        list.firstOrNull { element -> element.localDate == localDate }?.hourlyWeatherForecastList?.add(it)
            ?: run {
                list.add(
                    DailyWeatherForecast(
                        localDate = localDate,
                        hourlyWeatherForecastList = mutableListOf(it)
                    )
                )
            }
    }
    return list
}

fun HourlyWeatherForecast.getWeatherIconUrl(): String {
    return "${BuildConfig.IMG_BASE_URL}${this.weather.first().icon}@2x.png"
}

fun HourlyWeatherForecast.formatDateTime(): String {
    val date = Date(this.dt*1000)
    return date.toFormattedString()
}

