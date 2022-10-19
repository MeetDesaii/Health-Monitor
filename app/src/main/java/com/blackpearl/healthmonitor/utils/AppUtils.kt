package com.blackpearl.healthmonitor.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import java.util.*

object AppUtils {

    fun setReminder(context: Context, timeInMillis: Long, noti_id: Int, title: String?, msg: String?) {
        val intent = Intent(context, Receiver::class.java)
        intent.putExtra("msg", msg)
        intent.putExtra("title", title)
        intent.putExtra("time", timeInMillis)

        var resultPendingIntent : PendingIntent? = null
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
            resultPendingIntent = PendingIntent.getBroadcast(context, noti_id, intent, PendingIntent.FLAG_MUTABLE)
        }
        else{
            resultPendingIntent = PendingIntent.getBroadcast(context, noti_id, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, timeInMillis, resultPendingIntent)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeInMillis, resultPendingIntent)
        } else {
            alarmManager[AlarmManager.RTC_WAKEUP, timeInMillis] = resultPendingIntent
        }
    }


    fun getDailyReminderTime(cal: Calendar): Long {
        val currentCal = Calendar.getInstance()
        currentCal.timeInMillis = System.currentTimeMillis()
        if (cal.timeInMillis < System.currentTimeMillis()) {
//            cal.add(Calendar.MINUTE, 2);
            cal.add(Calendar.HOUR, 24)
        }
        return cal.timeInMillis
    }
}