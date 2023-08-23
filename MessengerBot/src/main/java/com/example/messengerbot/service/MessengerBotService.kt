package com.example.messengerbot.service

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat
import android.app.RemoteInput

class MessengerBotService : NotificationListenerService() {

    companion object {
        fun isNotificationPermissionAllowed(context: Context) =
            NotificationManagerCompat
                .getEnabledListenerPackages(context)
                .any { pkgName -> pkgName == context.packageName }

        private var listener: OnMessageReceivedListener? = null

        fun setOnMessageReceivedListener(listener: OnMessageReceivedListener) {
            this.listener = listener
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)
        val packageName = sbn?.packageName
        val notification = sbn?.notification
        val remoteInput = notification?.findRemoteInputActionPair(true)

        val title = notification?.extras?.getString(Notification.EXTRA_TITLE)
        val content = notification?.extras?.getString(Notification.EXTRA_TEXT)
        val subText = notification?.extras?.getString(Notification.EXTRA_SUB_TEXT)

        val message = NotificationMessage(
            title = title,
            content = content,
            subText = subText,
            packageName = packageName
        )
        println("content = $message")

        if (packageName != "com.kakao.talk") return
        remoteInput?.also { ri ->
            listener?.onMessageReceive(
                message = message,
                notification = notification,
                remoteInput = ri.first
            )
        }
    }
}

fun Notification.sendMessage(
    context: Context,
    remoteInput: RemoteInput,
    answer: String
) {
    val sendIntent = Intent()
    val msg = Bundle()
    actions?.forEach { action ->
        msg.putCharSequence(remoteInput.resultKey, answer)
        RemoteInput.addResultsToIntent(arrayOf(remoteInput), sendIntent, msg)
        try {
            action.actionIntent.send(context, 0, sendIntent)
        } catch (e: PendingIntent.CanceledException) {
            e.printStackTrace()
        }
    }
}
