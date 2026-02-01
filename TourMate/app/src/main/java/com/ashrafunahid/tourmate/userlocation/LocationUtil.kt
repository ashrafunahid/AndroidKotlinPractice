package com.ashrafunahid.tourmate.userlocation

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

fun isLocationPermissionGranted(context: Context) : Boolean =
    ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED

const val LOCATION_PERMISSION_REQUEST_CODE = 999

fun requestLocationPermission(activity: Activity) {
    activity.requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
}