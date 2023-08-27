# MessengerBot
Create your own MessengerBot for Any Messenger Apps

### How to use
in **grable.build(:app)**
```groovy
dependencies {
    implementation 'io.github.blucky8649:MessengerBot:0.0.2'
}
```


in **AndroidManifest.xml**
```xml
<application
...
<service
    android:name=".service.MessengerBotService"
    android:permission="android.permission.BIND_NOTIFICATION_LISTENER_SERVICE"
    android:exported="true">
    <intent-filter>
        <action android:name="android.service.notification.NotificationListenerService"/>
    </intent-filter>
</service>
</application>
```

in **Activity or Fragment**
```kotlin
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
        // you can use 'sendMessage(...)' to answer against notification
        notification.sendMessage(this, remoteInput, "ANSWER")
    }
...
```
