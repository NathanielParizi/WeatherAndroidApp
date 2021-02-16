package com.example.weatherandroid.DataSource.Remote

import com.example.weatherandroid.Model.WeatherApiResponse
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.Response

class WeatherRepository : KoinComponent {

    private val magicService: WeatherService by inject()

    suspend fun getWeather(
        city: String,
        key: String
    ): Response<WeatherApiResponse> {
        return magicService.getWeather(city, key)

    }

}