package com.example.weatherandroid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.weatherandroid.ViewModels.WeatherViewModel
import com.example.weatherandroid.databinding.FragmentDetailsBinding
import org.koin.android.viewmodel.ext.android.sharedViewModel

class DetailsFragment : Fragment() {

    private val weatherViewModel: WeatherViewModel by sharedViewModel()

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!  //read only variable

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pos = arguments?.getString("position")?.toInt() ?: 0
        weatherViewModel.detailsData.observe(viewLifecycleOwner, {
            binding.temperatureTextView.text =
                weatherViewModel.kelvinToFarenheit(it[pos].main.temp).toString()
            var feelsLikeString = "Feels like: "
            feelsLikeString += weatherViewModel.kelvinToFarenheit(it[pos].main.feels_like)
                .toString()
            binding.feelsLikeTextView.text = feelsLikeString
            binding.weatherDescription.text = it[pos].weather[0].main
            binding.weatherType.text = it[pos].weather[0].description
        })
        binding.topAppBar.setNavigationOnClickListener {
            // back button pressed
            findNavController().popBackStack()
        }
    }
}