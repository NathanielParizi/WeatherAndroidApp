package com.example.weatherandroid.ViewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherandroid.DataSource.Remote.API_KEY
import com.example.weatherandroid.DataSource.Remote.WeatherRepository
import com.example.weatherandroid.Model.Forecast
import com.example.weatherandroid.Model.WeatherApiResponse
import kotlinx.coroutines.launch
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

private const val TAG = "WeatherViewModel"

@KoinApiExtension
class WeatherViewModel : ViewModel(), KoinComponent {

    private val service: WeatherRepository by inject()
    var responseError = ""
    var detailsData = MutableLiveData<ArrayList<Forecast>>()

    private val weatherListMutableLiveData = MutableLiveData<WeatherApiResponse>()
    val weatherListLiveData: LiveData<WeatherApiResponse>
        get() = weatherListMutableLiveData

    fun kelvinToFarenheit(value: Double): Double {
        val farenheit = value * 1.8 - 459.67
        return String.format("%.2f", farenheit).toDouble()
    }

    fun basicCoroutineFetch(city: String) {

        viewModelScope.launch {
            val response = service.getWeather(city, API_KEY)
            if (response.isSuccessful && response.body() != null) {
                weatherListMutableLiveData.value = response.body()
            } else {
                responseError = response.errorBody().toString()
                onError(responseError)
            }
        }
    }

    private fun onError(message: String) {
        Log.d(TAG, "onError: $message")
    }
}