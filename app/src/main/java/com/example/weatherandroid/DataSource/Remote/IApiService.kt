package com.example.weatherandroid.DataSource.Remote

import com.example.weatherandroid.Model.WeatherApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IApiService {

    @GET(PATH_WEATHER)
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("appid") key: String
    ):Response<WeatherApiResponse>


}