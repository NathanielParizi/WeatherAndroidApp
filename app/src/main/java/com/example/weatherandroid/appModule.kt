package com.example.weatherandroid

import com.example.weatherandroid.DataSource.Remote.WeatherRepository
import com.example.weatherandroid.DataSource.Remote.WeatherService
import com.example.weatherandroid.ViewModels.WeatherViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.component.KoinApiExtension
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.DateFormat
import java.util.concurrent.TimeUnit

@OptIn(KoinApiExtension::class)
val appModule = module {

    viewModel { WeatherViewModel() }
    single { WeatherService() }
    single { WeatherRepository() }

    single {
        val gson: Gson = GsonBuilder()
            .enableComplexMapKeySerialization()
            .serializeNulls()
            .setDateFormat(DateFormat.LONG)
            .create()
        GsonConverterFactory.create()
    } bind GsonConverterFactory::class

    single {
        val loggingInterceptor = HttpLoggingInterceptor()
        val client = OkHttpClient.Builder()//.readTimeout(60, TimeUnit.SECONDS)
//            .connectTimeout(60, TimeUnit.SECONDS)
            .apply { HttpLoggingInterceptor.Level.BASIC }
            //.cache(null)
            .addInterceptor(loggingInterceptor)
        client.build()


    }

    single {
        val builder =
            Retrofit.Builder()
                .addConverterFactory(get<GsonConverterFactory>())
                //.client(get())
        builder
    }


}