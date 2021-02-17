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
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

private const val TAG = "WeatherViewModel"

class WeatherViewModel : ViewModel(), KoinComponent {

    val service: WeatherRepository by inject()
    var responseError = ""
    var userCity = MutableLiveData<String>()

    var detailsData = MutableLiveData<ArrayList<Forecast>>()

    private val weatherListMutableLiveData = MutableLiveData<WeatherApiResponse>()
    val weatherListLiveData: LiveData<WeatherApiResponse>
        get() = weatherListMutableLiveData


    val loading2 = MutableLiveData<Boolean>()
    val loadingError2 = MutableLiveData<String?>()
    var job2: Job? = null
    val exceptionHandler2 = CoroutineExceptionHandler { coroutineContext, throwable ->
        onError("Exception: ${throwable.localizedMessage}")
    }

    fun onError(message: String) {
//        loadingError2.value = message
//        loading2.value = false
//        Log.d("TAG", "onError: $message")
    }

    fun kelvinToFarenheit(value: Double): Double {
        var farenheit = value * 1.8 - 459.67
        return String.format("%.2f", farenheit).toDouble()
    }

    fun basicCoroutineFetch(city: String) {

        viewModelScope.launch {

            val response = service.getWeather(city, API_KEY)
            if (response.isSuccessful && response.body() != null) {
                Log.d(TAG, "TEST: ${response.body()}")
                weatherListMutableLiveData.value = response.body()
            } else {
                responseError = response.errorBody().toString()
            }
        }
    }
}