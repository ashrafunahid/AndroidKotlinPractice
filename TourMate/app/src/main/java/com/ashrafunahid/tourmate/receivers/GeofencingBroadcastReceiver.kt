package com.ashrafunahid.tourmate.receivers

import android.content.BroadcastReceiver
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent
import com.ashrafunahid.tourmate.R


class GeofencingBroadcastReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val event = GeofencingEvent.fromIntent(intent)
        var transitionString = ""
        if (event?.geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
            transitionString = "entered"
        } else {
            transitionString = "exited"
        }

        var nameList = mutableListOf<String>()

        event?.triggeringGeofences?.forEach {
            nameList.add(it.requestId)
        }

        val nameString = nameList.joinToString(separator = ",")
        sendNotification(context, transitionString, nameString)
    }

    private fun sendNotification(context: Context, transitionString: String, nameString: String) {
        var builder = NotificationCompat.Builder(context, "my_channel")
            .setSmallIcon(R.drawable.ic_baseline_notifications_paused_24)
            .setContentTitle("Location Alert")
            .setContentText("You have $transitionString $nameString")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Geofence Channel"
            val descriptionText = "Notification for Location Alert"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("my_channel", name, importance).apply {
                description = descriptionText
            }
            manager.createNotificationChannel(channel)
        }

        manager.notify(333, builder.build())
    }
}