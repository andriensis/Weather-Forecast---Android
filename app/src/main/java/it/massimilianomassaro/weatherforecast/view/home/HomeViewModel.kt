package it.massimilianomassaro.weatherforecast.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.massimilianomassaro.weatherforecast.core.*
import it.massimilianomassaro.weatherforecast.data.remote.api.WeatherForecastApiClient
import it.massimilianomassaro.weatherforecast.data.remote.model.DailyWeatherForecast
import it.massimilianomassaro.weatherforecast.data.remote.model.HourlyWeatherForecast
import it.massimilianomassaro.weatherforecast.data.remote.model.toDailyWeatherForecastList
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject constructor(
    private val weatherForecastApiClient: WeatherForecastApiClient
): ViewModel() {
    private val _state = MutableLiveData<ViewModelState>()
    val state: LiveData<ViewModelState> = _state

    private val _weatherForecastList = MutableLiveData<List<DailyWeatherForecast>>()
    val weatherForecastList: LiveData<List<DailyWeatherForecast>> = _weatherForecastList

    fun getWeatherForecastForCity(city: String) {
        if(city.isEmpty()) {
            _state.value = ErrorState(AppError.InvalidField)
            return
        }
        _state.value = LoadingState
        viewModelScope.launch {
            weatherForecastApiClient.getWeatherForecast(city).fold(
                ifLeft = {
                    Timber.e("Error while getting weather forecast -> $it")
                    _state.value = ErrorState(it)
                    _weatherForecastList.value = emptyList()
                },
                ifRight = {
                    _state.value = SuccessState()
                    _weatherForecastList.value = it.toDailyWeatherForecastList()
                }
            )
        }
    }
}