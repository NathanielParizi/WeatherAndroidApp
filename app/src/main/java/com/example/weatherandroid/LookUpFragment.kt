package com.example.weatherandroid

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.weatherandroid.ViewModels.WeatherViewModel
import com.example.weatherandroid.databinding.FragmentLookUpBinding
import org.koin.android.ext.android.inject

class LookUpFragment : Fragment() {

    val weatherViewModel: WeatherViewModel by inject()
    private var _binding: FragmentLookUpBinding? = null
    private val binding get() = _binding!!  //read only variable
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLookUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        weatherViewModel.weatherListLiveData.observe(viewLifecycleOwner, Observer {
            Log.d("GOLD", "onViewCreated: $it")
        })

        binding.lookUpBtn.setOnClickListener {



            findNavController().navigate(R.id.action_lookUpFragment_to_weatherListFragment,
                Bundle().apply{
                    putString("city",binding.lookUpCityEditText.text.toString())
                })
        }
    }


}