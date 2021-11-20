/*
 * HealthyHoneyTving © DND 해커톤 2조. all rights reserved.
 * HealthyHoneyTving license is under the MIT.
 *
 * [NotificationUtil.kt] created by Ji Sungbin on 21. 11. 20. 오후 3:38
 *  
 * Please see: https://github.com/DND-hackathon/HealthyHoneyTving-Android/blob/dev/LICENSE.
 */

package dnd.hackathon.second.healthyhoneytving.util.core

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

@Suppress("DEPRECATION")
object NotificationUtil {
    private fun builder(context: Context, channelId: String) =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationCompat.Builder(context, channelId)
        } else NotificationCompat.Builder(context)

    fun createChannel(context: Context, name: String, description: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getManager(context).createNotificationChannelGroup(
                NotificationChannelGroup(
                    name,
                    name
                )
            )

            val channelMessage =
                NotificationChannel(name, name, NotificationManager.IMPORTANCE_DEFAULT).apply {
                    this.description = description
                    group = name
                    enableVibration(false)
                }
            getManager(context).createNotificationChannel(channelMessage)
        }
    }

    fun requestNotificationListenerPermission(activity: Activity) {
        val intent = Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")
        activity.startActivity(intent)
    }

    fun isNotificationListenerPermissionGranted(context: Context) =
        NotificationManagerCompat.getEnabledListenerPackages(context).contains(context.packageName)

    private fun getManager(context: Context) =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun showNormalNotification(
        context: Context,
        id: Int,
        channelId: String,
        title: String,
        content: String,
        icon: Int,
        isOnGoing: Boolean,
        showTimestamp: Boolean,
    ) {
        getManager(context).notify(
            id,
            getNormalNotification(
                context,
                channelId,
                title,
                content,
                icon,
                isOnGoing,
                showTimestamp
            ).build()
        )
    }

    fun getNormalNotification(
        context: Context,
        channelId: String,
        title: String,
        content: String,
        @DrawableRes icon: Int,
        isOnGoing: Boolean,
        showTimestamp: Boolean,
    ) = builder(context, channelId)
        .setContentTitle(title)
        .setContentText(content)
        .setSmallIcon(icon)
        .setAutoCancel(true)
        .setOngoing(isOnGoing)
        .setWhen(System.currentTimeMillis())
        .setShowWhen(showTimestamp)
}
