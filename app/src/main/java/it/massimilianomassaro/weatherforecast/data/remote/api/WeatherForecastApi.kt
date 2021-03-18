package it.massimilianomassaro.weatherforecast.data.remote.api

import arrow.core.Either
import it.massimilianomassaro.weatherforecast.core.WFError
import it.massimilianomassaro.weatherforecast.data.remote.BaseRetrofitClient
import it.massimilianomassaro.weatherforecast.data.remote.mapResponse
import it.massimilianomassaro.weatherforecast.data.remote.model.HourlyWeatherForecast
import it.massimilianomassaro.weatherforecast.data.remote.model.response.ForecastResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject

interface WeatherForecastApi {
    @GET("forecast?units=metric")
    suspend fun getWeatherForecast(
        @Query("q") city: String
    ): Response<ForecastResponse>
}

class WeatherForecastApiClient
@Inject constructor(
    private val client: WeatherForecastApi
) : BaseRetrofitClient() {

    suspend fun getWeatherForecast(city: String): Either<WFError, List<HourlyWeatherForecast>> {
        return call { client.getWeatherForecast(city) }.mapResponse().map { it.list }
    }
}