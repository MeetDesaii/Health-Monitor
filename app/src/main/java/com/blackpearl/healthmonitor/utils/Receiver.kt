package com.blackpearl.healthmonitor.utils

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.blackpearl.healthmonitor.BuildConfig
import com.blackpearl.healthmonitor.R
import com.blackpearl.healthmonitor.ui.HomeActivity
import java.util.*

class Receiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra("title")
        val msg = intent.getStringExtra("msg")
        val time = intent.getLongExtra("time", 0)
        setNotification(context, title, msg)
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = time
        AppUtils.setReminder(
            context,
            AppUtils.getDailyReminderTime(calendar), System.currentTimeMillis().toInt(), title, msg
        )
    }

    private fun setNotification(context: Context, title: String?, msg: String?) {
        val intent = Intent(context, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        //        intent.putExtra("NOTIFICATION_TYPE", Integer.parseInt(String.valueOf(id)));
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationId = System.currentTimeMillis().toInt()
        val channelId = BuildConfig.APPLICATION_ID + "_reminder"
        val channelName = "Reminder"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val mChannel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            mChannel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
            Objects.requireNonNull(notificationManager).createNotificationChannel(mChannel)
        }
        val mBuilder = NotificationCompat.Builder(context, channelId)
            .setTicker(msg)
            .setContentTitle(title)
            .setContentText(msg) //                .setStyle(new NotificationCompat.BigTextStyle().bigText(NOTIFICATION_MSG))
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setAutoCancel(true)
        mBuilder.setSmallIcon(getNotificationIcon(context, mBuilder))
        val stackBuilder = TaskStackBuilder.create(context)
        stackBuilder.addNextIntent(intent)

        var resultPendingIntent : PendingIntent? =null

        resultPendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            stackBuilder.getPendingIntent(0, PendingIntent.FLAG_MUTABLE)
        } else {
            stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }
        mBuilder.setContentIntent(resultPendingIntent)
        Objects.requireNonNull(notificationManager).notify(notificationId, mBuilder.build())
    }

    private fun getNotificationIcon(
        context: Context,
        notificationBuilder: NotificationCompat.Builder
    ): Int {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val color = context.resources.getColor(R.color.purple_200)
            notificationBuilder.color = color
            return R.mipmap.ic_launcher
        }
        return R.mipmap.ic_launcher
    }
}