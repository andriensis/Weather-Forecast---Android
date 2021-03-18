package it.massimilianomassaro.weatherforecast.data.remote.model

import it.massimilianomassaro.weatherforecast.BuildConfig
import it.massimilianomassaro.weatherforecast.fixtures.DailyWeatherForecastFixture
import it.massimilianomassaro.weatherforecast.fixtures.HourlyWeatherForecastFixture
import junit.framework.Assert.assertEquals
import org.junit.Test

class HourlyWeatherForecastTest {

    @Test
    fun testToDailyWeatherForecastList() {
        val dailyWeatherForecast = HourlyWeatherForecastFixture.getHourlyWeatherForecast().toDailyWeatherForecastList()
        val actualDailyWeatherForecast = DailyWeatherForecastFixture.getDailyWeatherForecast()
        assertEquals(actualDailyWeatherForecast, dailyWeatherForecast)
    }

    @Test
    fun testGetWeatherIconUrl() {
        val hourlyWeatherForecast = HourlyWeatherForecastFixture.getHourlyWeatherForecast().first()
        assertEquals("${BuildConfig.IMG_BASE_URL}${hourlyWeatherForecast.weather.first().icon}@2x.png", hourlyWeatherForecast.getWeatherIconUrl())
    }

    @Test
    fun testFormatDateTime() {
        val hourlyWeatherForecast = HourlyWeatherForecastFixture.getHourlyWeatherForecast().first()
        assertEquals("18/03/2021 14:00", hourlyWeatherForecast.formatDateTime())
    }

}