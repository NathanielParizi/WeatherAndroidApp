package com.example.weatherandroid.DataSource.Remote

import com.example.weatherandroid.Model.WeatherApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IApiService {

    @GET(PATH_WEATHER)
    suspend fun getWeather(
        @Query(QUERY_PARAM_CITY) city: String,
        @Query(QUERY_PARAM_API_KEY) key: String
    ):Response<WeatherApiResponse>


}