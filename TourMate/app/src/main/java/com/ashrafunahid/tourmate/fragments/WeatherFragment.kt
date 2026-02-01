package com.ashrafunahid.tourmate.fragments

import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.ashrafunahid.tourmate.R
import com.ashrafunahid.tourmate.databinding.FragmentWeatherBinding
import com.ashrafunahid.tourmate.pref.getTempStatus
import com.ashrafunahid.tourmate.pref.setTempStatus
import com.ashrafunahid.tourmate.userlocation.LOCATION_PERMISSION_REQUEST_CODE
import com.ashrafunahid.tourmate.userlocation.isLocationPermissionGranted
import com.ashrafunahid.tourmate.userlocation.requestLocationPermission
import com.ashrafunahid.tourmate.viewmodels.LocationViewModel
import com.ashrafunahid.tourmate.viewmodels.WeatherViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class WeatherFragment : Fragment() {

    private lateinit var binding: FragmentWeatherBinding
    private val locationViewModel: LocationViewModel by activityViewModels()
    private val weatherViewModel: WeatherViewModel by viewModels()
    private lateinit var preference: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        preference = requireActivity().getSharedPreferences("weather_prefs", Context.MODE_PRIVATE)
        binding = FragmentWeatherBinding.inflate(inflater, container, false)

        binding.tempSwitch.isChecked = getTempStatus(preference)
        weatherViewModel.tempStatus = getTempStatus(preference)

        locationViewModel.location.observe(viewLifecycleOwner, {location ->
            weatherViewModel.getWeatherData(location)
        })

        weatherViewModel.current.observe(viewLifecycleOwner, {currentWeatherModel ->
            binding.current = currentWeatherModel
        })

        binding.tempSwitch.setOnCheckedChangeListener { btn, isChecked ->
            setTempStatus(isChecked, preference.edit())
            weatherViewModel.tempStatus = isChecked
            weatherViewModel.getWeatherData(locationViewModel.location.value!!)
        }

        return binding.root
    }

}