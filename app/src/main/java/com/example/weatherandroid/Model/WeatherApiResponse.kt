package com.example.weatherandroid.Model

data class WeatherApiResponse(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<Forecast>,
    val message: Int
)