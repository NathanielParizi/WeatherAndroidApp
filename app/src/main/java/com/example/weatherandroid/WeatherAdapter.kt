package com.example.weatherandroid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherandroid.Model.Forecast
import com.example.weatherandroid.Model.Main
import com.example.weatherandroid.databinding.RowWeatherBinding

class WeatherAdapter: RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    private var dataSet: List<Forecast> = emptyList()


    fun setDataSet(dataSet: List<Forecast>){
        this.dataSet = dataSet
        notifyDataSetChanged()
    }

    class WeatherViewHolder(val weatherView: RowWeatherBinding)
        : RecyclerView.ViewHolder(weatherView.root){

        fun onBind(rowTemp: Main){
            /*weatherView.weatherTxtView.text =
                weatherView.root.context.getString(R.string.prefix_feels_like, rowTemp.feels_like.toString())
            weatherView.tempTextView.text =
                weatherView.root.context.getString(R.string.prefix_temp,rowTemp.temp.toString())*/
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val itemBinding = RowWeatherBinding
            .inflate(LayoutInflater.from(parent.context),
                parent,
                false)
        return WeatherViewHolder(itemBinding)
    }

    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.onBind(dataSet[position].main)
    }
}