package com.ashrafunahid.tourmate.network

import com.ashrafunahid.tourmate.models.CurrentWeatherModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url

const val weather_api_key = "d3400429bb76019df8849de640805e6d"
const val weather_api_key_app = "cf2c61cecae9d540d44beb6b6cafa835"
const val base_url = "https://api.openweathermap.org/data/2.5/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl(base_url)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

interface WeatherServiceApi {
    @GET
    suspend fun getCurrentWeather(@Url endUrl: String): CurrentWeatherModel
}

object WeatherApi {
    val weatherServiceApi: WeatherServiceApi by lazy {
        retrofit.create(WeatherServiceApi::class.java)
    }
}