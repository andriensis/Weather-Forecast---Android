package it.massimilianomassaro.weatherforecast.data.remote.model.response

import it.massimilianomassaro.weatherforecast.data.remote.model.HourlyWeatherForecast

data class ForecastResponse(
    val list: List<HourlyWeatherForecast>
)