package com.ashrafunahid.todo.workmanagerutils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.ashrafunahid.todo.R

class NotificationWorker(val context: Context, params: WorkerParameters): Worker(context, params) {
    override fun doWork(): Result {
        val name = inputData.getString("name")
        sendNotification(context, name)
        return Result.success()
    }

    private fun sendNotification(context: Context, name: String?) {
        val builder = NotificationCompat.Builder(context, "my_channel")
            .setSmallIcon(R.drawable.baseline_notifications_24)
            .setContentTitle("ToDo Notification")
            .setContentText("Time to do $name")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val manager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("my_channel", "text", NotificationManager.IMPORTANCE_DEFAULT)
            channel.description = "This channel sends todo scheduling notifications"
            manager.createNotificationChannel(channel)
        }

        manager.notify(1, builder.build())
    }
}