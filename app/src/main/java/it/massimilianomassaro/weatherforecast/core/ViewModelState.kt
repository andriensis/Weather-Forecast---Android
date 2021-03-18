package it.massimilianomassaro.weatherforecast.core

sealed class ViewModelState
data class SuccessState(val result: Any? = null): ViewModelState()
object LoadingState: ViewModelState()
data class ErrorState(val error: WFError? = null): ViewModelState()


