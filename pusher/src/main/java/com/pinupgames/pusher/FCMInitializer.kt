package com.pinupgames.pusher

import fuel.Fuel
import fuel.post
import fuel.put

object FCMInitializer {

    private const val TAG = "FCMPusher"

    /**
     * Register a user with the specific details in the system
     *
     * @param gadid The unique identifier for the user's device.
     * @param country The country of residence for the user.
     * @param language The preferred language of the user.
     * @param appPackage The package name of the application.
     * @throws FuelError if there is an error in the network request.
     */
    suspend fun register(
        gadid: String,
        country: String,
        language: String,
        appPackage: String
    ): Int {
        val bodyJson = """
            {
                "gadid": "$gadid",
                "country": "$country",
                "language": "$language",
                "appPackage": "$appPackage"
            }
        """.trimIndent()

        return Fuel.post(
            url = "$PUSH_SERVICE_URL/adduser", body = bodyJson
        ).statusCode
    }

    /**
     * Sends the FCM token, app package, and GADID to the server for updating the token.
     *
     * @param fcmToken The FCM token to send.
     * @param appPackage The package name of the application.
     * @param gadid The unique identifier associated with the device.
     * @throws FuelError if there was an error sending the request.
     */
    suspend fun sendToken(fcmToken: String, appPackage: String, gadid: String): Int {
        val bodyJson = """
            {
                "fcmtoken": $fcmToken",
                "app_package": $appPackage
            }
        """.trimIndent()

        return Fuel
            .put(
                url = "$PUSH_SERVICE_URL/updatetoken",
                parameters = listOf("gadid" to gadid),
                body = bodyJson
            ).statusCode
    }

    /**
     * Sends the tag, app package, and GADID to the server for updating the tag.
     *
     * @param tag The tag to send.
     * @param appPackage The package name of the application.
     * @param gadid The unique identifier associated with the device.
     * @throws FuelError if there was an error sending the request.
     */
    suspend fun sendTag(tag: String, appPackage: String, gadid: String): Int {
        val bodyJson = """
            {
                "tag": $tag",
                "app_package": $appPackage
            }
        """.trimIndent()

        return Fuel
            .put(
                url = "$PUSH_SERVICE_URL/updatetag",
                parameters = listOf("gadid" to gadid),
                body = bodyJson
            ).statusCode
    }

    const val PUSH_SERVICE_URL = "https://aff-demo-push.herokuapp.com"
}