package com.pinupgames.pusher

import fuel.Fuel
import fuel.post

object NotificationManager {

    /**
     * Sends a notification to a specific device using the Firebase Cloud Messaging service.
     *
     * @param gadid The unique identifier of the device to send the notification to.
     * @param appPackage The package name of the application receiving the notification.
     * @param title The title of the notification.
     * @param body The body content of the notification.
     * @return The status code indicating the success or failure of the notification request.
     */
    suspend fun sendNotification(
        gadid: String,
        appPackage: String,
        title: String,
        body: String
    ): Int {
        val bodyJson = """
            {
                "gadid": $gadid",
                "app_package": $appPackage",
                "title": $title",
                "body": $body"
            }
        """.trimIndent()

        return Fuel
            .post(
                url = "${FCMInitializer.PUSH_SERVICE_URL}/sendNotification",
                body = bodyJson
            ).statusCode
    }
}