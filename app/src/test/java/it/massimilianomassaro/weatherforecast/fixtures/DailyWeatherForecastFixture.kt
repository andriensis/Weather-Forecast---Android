package it.massimilianomassaro.weatherforecast.fixtures

import it.massimilianomassaro.weatherforecast.data.remote.model.DailyWeatherForecast
import java.time.LocalDate

class DailyWeatherForecastFixture {
    companion object {
        fun getDailyWeatherForecast(): List<DailyWeatherForecast> = listOf(
            DailyWeatherForecast(
                localDate = LocalDate.of(2021, 3, 18),
                hourlyWeatherForecastList = mutableListOf(HourlyWeatherForecastFixture.getHourlyWeatherForecast()[0])
            ),
            DailyWeatherForecast(
                localDate = LocalDate.of(2021, 3, 19),
                hourlyWeatherForecastList = mutableListOf(HourlyWeatherForecastFixture.getHourlyWeatherForecast()[1])
            )
        )
    }
}