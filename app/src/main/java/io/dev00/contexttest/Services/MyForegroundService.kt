package io.dev00.contexttest.Services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class MyForegroundService : Service() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val t = Thread {
            while (true) {
                Thread.sleep(1000)
                Log.d("TAG", "onStartCommand: ")
            }
        }
        t.start()
        val CHANNEL_ID = "Foreground Service ID"
        val notificationChannel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_ID,
            NotificationManager.IMPORTANCE_LOW
        )
        getSystemService(NotificationManager::class.java).createNotificationChannel(
            notificationChannel
        )
        val notification =
            Notification.Builder(this, CHANNEL_ID).setContentText("Service is Running")
                .setContentTitle("Service working").build()
        startForeground(1001,notification)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

}