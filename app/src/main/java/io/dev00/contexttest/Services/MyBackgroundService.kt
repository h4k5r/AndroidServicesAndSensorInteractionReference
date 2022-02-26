package io.dev00.contexttest.Services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class MyBackgroundService: Service() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val t = Thread {
            while (true) {
                Thread.sleep(1000)
                Log.d("TAG", "onStartCommand: ")
            }
        }
        t.start()
        return super.onStartCommand(intent, flags, startId)
    }
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

}