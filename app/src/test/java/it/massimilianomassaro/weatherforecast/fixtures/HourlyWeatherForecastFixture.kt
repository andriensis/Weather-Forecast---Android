package it.massimilianomassaro.weatherforecast.fixtures

import it.massimilianomassaro.weatherforecast.data.remote.model.DailyWeatherForecast
import it.massimilianomassaro.weatherforecast.data.remote.model.HourlyWeatherForecast
import it.massimilianomassaro.weatherforecast.data.remote.model.WeatherData
import it.massimilianomassaro.weatherforecast.data.remote.model.WeatherDataMain
import java.time.LocalDate

class HourlyWeatherForecastFixture {
    companion object {
        fun getHourlyWeatherForecast(): List<HourlyWeatherForecast> = listOf(
            HourlyWeatherForecast(
                dt = 1616072400, // 2021-03-18 14:00:00
                main = WeatherDataMain(
                    temp = 10.0
                ),
                weather = listOf(
                    WeatherData(
                        description = "clear sky",
                        icon = "01d"
                    )
                )
            ),
            HourlyWeatherForecast(
                dt = 1616169600, // 2021-03-19 16:00:00
                main = WeatherDataMain(
                    temp = 10.0
                ),
                weather = listOf(
                    WeatherData(
                        description = "few clouds",
                        icon = "02d"
                    )
                )
            )
        )
    }
}