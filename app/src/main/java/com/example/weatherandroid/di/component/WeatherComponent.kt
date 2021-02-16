package com.example.weatherandroid.di.component

import com.example.weatherandroid.MainActivity
import com.example.weatherandroid.di.module.ServiceModule

//@Component(modules={ServiceModule::class.java})
interface WeatherComponent {
    fun inject(activity: MainActivity)
}