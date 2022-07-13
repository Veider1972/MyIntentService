package ru.veider.myintentservice.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat

abstract class MyIntentService(private val name: String) : Service() {

    override fun onCreate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                name,
                name,
                NotificationManager.IMPORTANCE_NONE
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
        val notification: Notification = NotificationCompat.Builder(this, name)
            .setContentTitle("Сервис $name запущен")
            .build()
        startForeground(1, notification)
        super.onCreate()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    abstract fun onHandleIntent(intent: Intent?)

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Thread {
            onHandleIntent(intent)
        }.start()
        return super.onStartCommand(intent, flags, startId)
    }
}