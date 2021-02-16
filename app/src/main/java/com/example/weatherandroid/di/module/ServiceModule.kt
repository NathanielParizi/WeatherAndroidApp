package com.example.weatherandroid.di.module

import com.example.weatherandroid.DataSource.Remote.WeatherService

//@Module
class ServiceModule {
//    @Provide
    fun provideWeatherService() = WeatherService()
}