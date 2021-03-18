package it.massimilianomassaro.weatherforecast.data.remote

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.left
import arrow.core.right
import it.massimilianomassaro.weatherforecast.core.WFError
import it.massimilianomassaro.weatherforecast.core.ServerError
import retrofit2.Response

internal fun <T> Either<WFError, Response<T>>.mapResponse(): Either<WFError, T> =
    this.flatMap { response ->
        if (response.isSuccessful) {
            response.body()!!.right()
        } else {
            // We could use specific errors according to the backend response
                when(response.code()) {
                    404 -> ServerError.NotFound.left()
                    else -> ServerError.Generic.left()
            }
        }
    }