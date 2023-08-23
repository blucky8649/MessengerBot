package com.example.messengerbot.service

import android.app.Notification
import android.app.RemoteInput

fun interface OnMessageReceivedListener {
    fun onMessageReceive(
        message: NotificationMessage,
        notification: Notification,
        remoteInput: RemoteInput
    )
}