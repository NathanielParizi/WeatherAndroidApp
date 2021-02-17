package com.example.weatherandroid.DataSource.Remote

import com.example.weatherandroid.Model.WeatherApiResponse
import org.koin.core.component.KoinComponent
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherService : KoinComponent {


    suspend fun getWeather(city: String, key: String): Response<WeatherApiResponse> {

        val apiService: IApiService = Retrofit.Builder()
                .baseUrl(WEATHER_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(IApiService::class.java)

        return apiService.getWeather(city, key)
    }
}