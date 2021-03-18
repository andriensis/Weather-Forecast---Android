package it.massimilianomassaro.weatherforecast.di

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import it.massimilianomassaro.weatherforecast.BuildConfig
import it.massimilianomassaro.weatherforecast.data.remote.interceptor.AppIdInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @IntoSet
    @Provides
    fun provideAppIdInterceptor(impl: AppIdInterceptor): Interceptor = impl

    @Provides
    fun provideMoshi(): MoshiConverterFactory {
        val moshi = Moshi.Builder()
            .build()

        return MoshiConverterFactory.create(moshi)
    }

    @Provides
    fun provideRetrofit(
        interceptor: MutableSet<Interceptor>,
        moshiConverterFactory: MoshiConverterFactory
    ): Retrofit {
        val builder = OkHttpClient.Builder()

        interceptor.forEach {
            builder.addInterceptor(it)
        }

        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            builder.addInterceptor(loggingInterceptor)
        }

        val timeout: Long = 30
        val client = builder
            .readTimeout(timeout, TimeUnit.SECONDS)
            .connectTimeout(timeout, TimeUnit.SECONDS)
            .callTimeout(timeout, TimeUnit.SECONDS)
            .writeTimeout(timeout, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.REMOTE_BASE_URL)
            .client(client)
            .addConverterFactory(moshiConverterFactory)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
}