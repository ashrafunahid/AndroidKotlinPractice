package com.ashrafunahid.tourmate.viewmodels

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LocationViewModel: ViewModel() {
    private val currentLocation = MutableLiveData<Location>()

    val location: LiveData<Location>
        get() = currentLocation

    fun setNewLocation(location: Location) {
        currentLocation.value = location
    }
}