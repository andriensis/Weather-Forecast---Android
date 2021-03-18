package it.massimilianomassaro.weatherforecast.data.remote

import arrow.core.Either
import arrow.core.Try
import it.massimilianomassaro.weatherforecast.core.NetworkNotAvailableError
import it.massimilianomassaro.weatherforecast.core.WFError
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

open class BaseRetrofitClient
@Inject
constructor() {
    // Custom method I used to safely call a coroutine function or return a generic error (no network error in this case)
    internal suspend fun <T> call(call: suspend CoroutineScope.() -> Response<T>): Either<WFError, Response<T>> =
        withContext(Dispatchers.IO) {
            Try {
                call()
            }.toEither {
                Timber.e("Call error: $it")
                NetworkNotAvailableError
            }
        }
}