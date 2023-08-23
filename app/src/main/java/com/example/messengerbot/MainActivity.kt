package com.example.messengerbot

import android.app.Notification
import android.app.RemoteInput
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Messenger
import com.example.messengerbot.service.MessengerBotService
import com.example.messengerbot.service.NotificationMessage
import com.example.messengerbot.service.OnMessageReceivedListener
import com.example.messengerbot.service.sendMessage

class MainActivity : AppCompatActivity(), OnMessageReceivedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!MessengerBotService.isNotificationPermissionAllowed(this)) {
            startActivity(Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
        }
        MessengerBotService.setOnMessageReceivedListener(this)
    }

    override fun onMessageReceive(
        message: NotificationMessage,
        notification: Notification,
        remoteInput: RemoteInput
    ) {
        if (message.packageName == "com.kakao.talk")
            notification.sendMessage(this, remoteInput, "뷁~~")
    }
}