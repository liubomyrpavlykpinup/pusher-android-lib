package com.pinupgames.pusher

import com.google.firebase.messaging.FirebaseMessagingService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PushFirebaseMessagingService : FirebaseMessagingService() {

    private val scope = CoroutineScope(Dispatchers.IO + Job())

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    private fun sendRegistrationToServer(token: String) {
        scope.launch {
            val gadid = MainActivity.fetchGadid(applicationContext)

            PushService.instance().sendToken(
                fcmToken = token,
                gadid = gadid,
                appPackage = applicationContext.packageName
            )
        }
    }

}