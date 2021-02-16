package com.example.weatherandroid

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.weatherandroid.ViewModels.WeatherViewModel
import com.example.weatherandroid.databinding.FragmentWeatherListBinding
import org.koin.android.viewmodel.ext.android.sharedViewModel

private const val TAG = "WeatherListFragment"

class WeatherListFragment : Fragment() {

    val weatherViewModel: WeatherViewModel by sharedViewModel()
    var listItems = arrayListOf<String>()
    var listMap = HashMap<String, Double>()
    var keyList = ArrayList<String>()
    var ValueList = ArrayList<Double>()


    private var _binding: FragmentWeatherListBinding? = null
    private val binding get() = _binding!!  //read only variable
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWeatherListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val city = arguments?.getString("city")
        val listView = binding.listview
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, listItems)
        listView.adapter = adapter

        Log.d(TAG, "outside: ${city}")

        city?.let { weatherViewModel.basicCoroutineFetch(it) }


        weatherViewModel.weatherListLiveData.observe(viewLifecycleOwner, Observer {
            it.list.forEach {

                listItems.add("${it.weather[0].main}  ${it.main.temp}")
                Log.d(
                    TAG,
                    "this is my list: ${it.weather[0].main}  and   ${kelvinToFarenheit(it.main.temp)}"
                )
                listMap.put(it.weather[0].main, it.main.temp)

                keyList = ArrayList(listMap.keys)
                ValueList = ArrayList(listMap.values)
                listItems.clear()
                for (i in 0..keyList.size-1) {
                    listItems.add(keyList[i] + " " + ValueList[i])
                }
                listItems.forEach {
                    Log.d(TAG, "onViewCreated: ${listItems}")
                }

            }

            adapter.notifyDataSetChanged()
        })

        listView.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(requireContext(), listItems[position].toString(), LENGTH_LONG).show()
        }

//            findNavController().navigate(R.id.action_weatherListFragment_to_detailsFragment)

    }

    private fun kelvinToFarenheit(value: Double): Double {
        var farenheit = value * 1.8 - 459.67
        return farenheit
    }

}