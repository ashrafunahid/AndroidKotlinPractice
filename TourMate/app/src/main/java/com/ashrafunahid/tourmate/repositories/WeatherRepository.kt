package com.ashrafunahid.tourmate.repositories

import android.location.Location
import com.ashrafunahid.tourmate.models.CurrentWeatherModel
import com.ashrafunahid.tourmate.network.WeatherApi
import com.ashrafunahid.tourmate.network.weather_api_key

class WeatherRepository {
    suspend fun fetchCurrentWeatherData(location: Location, tempStatus: Boolean): CurrentWeatherModel?{
        val unit = if (tempStatus) "imperial" else "metric"
        val endUrl = "weather?lat=${location.latitude}&lon=${location.longitude}&units=$unit&appid=$weather_api_key"
        val currentWeatherModel: CurrentWeatherModel = WeatherApi.weatherServiceApi.getCurrentWeather(endUrl)
        return currentWeatherModel
    }
}