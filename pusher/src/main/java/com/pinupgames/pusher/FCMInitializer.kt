package com.pinupgames.pusher

import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface PushService {
    suspend fun register(
        gadid: String, country: String, language: String, appPackage: String
    ): Response

    suspend fun sendToken(fcmToken: String, appPackage: String, gadid: String): Response

    suspend fun sendTag(tag: String, appPackage: String, gadid: String): Response

    suspend fun sendNotification(
        gadid: String,
        appPackage: String,
        title: String,
        body: String
    )

    companion object {
        private const val PUSH_SERVICE_URL = "https://aff-demo-push.herokuapp.com/"

        fun instance(): PushService {
            val serviceApi = Retrofit.Builder()
                .baseUrl(PUSH_SERVICE_URL)
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .build()
                .create(FCMPushServiceApi::class.java)

            return FCMPushService(serviceApi)
        }
    }
}

internal class FCMPushService(
    private val serviceApi: FCMPushServiceApi
) : PushService {

    /**
     * Register a user with the specific details in the system
     *
     * @param gadid The unique identifier for the user's device.
     * @param country The country of residence for the user.
     * @param language The preferred language of the user.
     * @param appPackage The package name of the application.
     * @throws HttpException if there is an error in the network request.
     */
    override suspend fun register(
        gadid: String, country: String, language: String, appPackage: String
    ): Response {
        return serviceApi.register(
            requestBody = RegisterRequestBody(
                gadid = gadid, country = country, language = language, appPackage = appPackage
            )
        )
    }

    /**
     * Sends the FCM token, app package, and GADID to the server for updating the token.
     *
     * @param fcmToken The FCM token to send.
     * @param appPackage The package name of the application.
     * @param gadid The unique identifier associated with the device.
     * @throws HttpException if there was an error sending the request.
     */
    override suspend fun sendToken(fcmToken: String, appPackage: String, gadid: String): Response {
        return serviceApi.updateToken(
            requestBody = UpdateTokenRequestBody(
                fcmtoken = fcmToken,
                appPackage = appPackage
            ),
            gadid = gadid
        )
    }

    /**
     * Sends the tag, app package, and GADID to the server for updating the tag.
     *
     * @param tag The tag to send.
     * @param appPackage The package name of the application.
     * @param gadid The unique identifier associated with the device.
     * @throws HttpException if there was an error sending the request.
     */
    override suspend fun sendTag(tag: String, appPackage: String, gadid: String): Response {
        return serviceApi.updateTag(
            updateTagRequestBody = UpdateTagRequestBody(
                tag = tag,
                appPackage = appPackage
            ),
            gadid = gadid
        )
    }

    /**
     * Sends a notification to a specific device using the Firebase Cloud Messaging service.
     *
     * @param gadid The unique identifier of the device to send the notification to.
     * @param appPackage The package name of the application receiving the notification.
     * @param title The title of the notification.
     * @param body The body content of the notification.
     * @return The status code indicating the success or failure of the notification request.
     */
    override suspend fun sendNotification(
        gadid: String,
        appPackage: String,
        title: String,
        body: String
    ) {
        serviceApi.sendNotification(
            requestBody = SendNotificationBody(
                gadid = gadid,
                appPackage = appPackage,
                title = title,
                body = body
            )
        )
    }
}