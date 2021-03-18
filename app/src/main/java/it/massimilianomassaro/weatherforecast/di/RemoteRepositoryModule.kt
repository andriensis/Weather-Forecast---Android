package it.massimilianomassaro.weatherforecast.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import it.massimilianomassaro.weatherforecast.data.remote.api.WeatherForecastApi
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object RemoteRepositoryModule {

    @Provides
    fun provideWeatherForecastApi(retrofit: Retrofit): WeatherForecastApi {
        return retrofit.create(WeatherForecastApi::class.java)
    }
}