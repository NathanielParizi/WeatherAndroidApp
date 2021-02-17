package com.example.weatherandroid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.weatherandroid.Model.Forecast
import com.example.weatherandroid.ViewModels.WeatherViewModel
import com.example.weatherandroid.databinding.FragmentWeatherListBinding
import org.koin.android.viewmodel.ext.android.sharedViewModel


class WeatherListFragment : Fragment() {

    private val weatherViewModel: WeatherViewModel by sharedViewModel()
    private var listItems = arrayListOf<String>()
    private var listMap = HashMap<String, Forecast>()
    private var keyList = ArrayList<String>()
    private var valueList = ArrayList<Forecast>()


    private var _binding: FragmentWeatherListBinding? = null
    private val binding get() = _binding!!  //read only variable
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val city = arguments?.getString("city")
        val listView = binding.listview
        val adapter = ArrayAdapter(requireContext(), R.layout.row_textview, listItems)
        listView.adapter = adapter
        city?.let { weatherViewModel.basicCoroutineFetch(it) }
        weatherViewModel.weatherListLiveData.observe(viewLifecycleOwner, { it ->
            it.list.forEach {

                listItems.add("${it.weather[0].main}  ${it.main.temp}")
                listMap[it.weather[0].main] = it
                keyList = ArrayList(listMap.keys)
                valueList = ArrayList(listMap.values)
                listItems.clear()
                for (i in 0 until keyList.size) {
                    listItems.add(
                        keyList[i] + " \t Temp: " + weatherViewModel.kelvinToFarenheit(
                            valueList[i].main.temp
                        )
                    )
                }
            }
            weatherViewModel.detailsData.value = valueList
            adapter.notifyDataSetChanged()
        })

        listView.setOnItemClickListener { _, _, position, _ ->
            Toast.makeText(requireContext(), listItems[position], LENGTH_LONG).show()
            findNavController().navigate(R.id.action_weatherListFragment_to_detailsFragment,
                Bundle().apply {
                    putString("position", position.toString())
                })
        }

        binding.topAppBar.setNavigationOnClickListener {
            // back button pressed
            findNavController().popBackStack()
        }
    }



}