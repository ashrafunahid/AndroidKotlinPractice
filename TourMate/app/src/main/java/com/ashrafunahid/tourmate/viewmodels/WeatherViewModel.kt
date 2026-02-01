package com.ashrafunahid.tourmate.viewmodels

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashrafunahid.tourmate.models.CurrentWeatherModel
import com.ashrafunahid.tourmate.repositories.WeatherRepository
import kotlinx.coroutines.launch

class WeatherViewModel: ViewModel() {
    private val repository = WeatherRepository()
    private val _currentData = MutableLiveData<CurrentWeatherModel>()

    var tempStatus = false

    val current: LiveData<CurrentWeatherModel>
        get() = _currentData

    fun getWeatherData(location: Location) {
        viewModelScope.launch {
            try{
                _currentData.value = repository.fetchCurrentWeatherData(location, tempStatus)
            } catch (e: Exception) {

            }
        }
    }
}