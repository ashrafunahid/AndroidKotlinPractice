package com.ashrafunahid.tourmate.pref

import android.content.Context
import android.content.SharedPreferences
//import com.google.android.gms.maps.model.LatLng

const val temp_status = "status"
const val lat = "lat"
const val lng = "lng"

fun getPreference(context: Context) : SharedPreferences = context.getSharedPreferences("weather_prefs", Context.MODE_PRIVATE)

fun setTempStatus(status: Boolean, editor: SharedPreferences.Editor) {
    editor.putBoolean(temp_status, status)
    editor.commit()
}

fun getTempStatus(preferences: SharedPreferences) = preferences.getBoolean(temp_status, false)

//fun setLatLng(latLng: LatLng, editor: SharedPreferences.Editor) {
//    editor.putFloat(lat, latLng.latitude.toFloat())
//    editor.putFloat(lng, latLng.longitude.toFloat())
//    editor.commit()
//}
//
//fun getLatLng(preferences: SharedPreferences) : LatLng{
//    val lat = preferences.getFloat(lat, 0f)
//    val lng = preferences.getFloat(lng, 0f)
//    return LatLng(lat.toDouble(), lng.toDouble())
//}