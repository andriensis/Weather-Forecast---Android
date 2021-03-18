package it.massimilianomassaro.weatherforecast.core

sealed class WFError

sealed class AppError: WFError() {
    object InvalidField: AppError()
}

sealed class ServerError : WFError() {
    object Generic: ServerError()
    object NotFound: ServerError()
}

object NetworkNotAvailableError: WFError()