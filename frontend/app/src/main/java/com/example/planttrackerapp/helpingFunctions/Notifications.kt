package com.example.planttrackerapp.helpingFunctions

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.getSystemService


class Notifications {

    fun startCounting(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager;
        val intent = Intent(context, MyBroadcastReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context, 123, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        );
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis(),
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }
}