package com.example.messengerbot.service

data class NotificationMessage(
    val title: String? = "",
    val content: String? = "",
    val subText: String? = "",
    val packageName: String? = ""
)