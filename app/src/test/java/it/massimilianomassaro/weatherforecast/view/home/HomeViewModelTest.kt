package it.massimilianomassaro.weatherforecast.view.home

import arrow.core.left
import arrow.core.right
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.OverrideMockKs
import io.mockk.impl.annotations.RelaxedMockK
import it.massimilianomassaro.weatherforecast.WFTestRule
import it.massimilianomassaro.weatherforecast.core.ErrorState
import it.massimilianomassaro.weatherforecast.core.ServerError
import it.massimilianomassaro.weatherforecast.core.SuccessState
import it.massimilianomassaro.weatherforecast.data.remote.api.WeatherForecastApiClient
import it.massimilianomassaro.weatherforecast.fixtures.DailyWeatherForecastFixture
import it.massimilianomassaro.weatherforecast.fixtures.HourlyWeatherForecastFixture
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {
    @get:Rule
    var coroutinesTestRule = WFTestRule()

    @RelaxedMockK
    lateinit var weatherForecastApiClient: WeatherForecastApiClient

    @OverrideMockKs
    lateinit var sut: HomeViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @Test
    fun getWeatherForecastSuccess() {
        coEvery { weatherForecastApiClient.getWeatherForecast(any()) }.returns(HourlyWeatherForecastFixture.getHourlyWeatherForecast().right())
        sut.getWeatherForecastForCity("city")
        assert(sut.state.value is SuccessState)
        assertEquals(DailyWeatherForecastFixture.getDailyWeatherForecast().size, sut.weatherForecastList.value?.size)
        assertEquals(DailyWeatherForecastFixture.getDailyWeatherForecast(), sut.weatherForecastList.value)
    }

    @Test
    fun getWeatherForecastError() {
        coEvery { weatherForecastApiClient.getWeatherForecast(any()) }.returns(ServerError.NotFound.left())
        sut.getWeatherForecastForCity("city")
        assert(sut.state.value is ErrorState)
        assertEquals(0, sut.weatherForecastList.value?.size)
    }
}