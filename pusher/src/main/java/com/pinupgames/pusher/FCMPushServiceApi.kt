package com.pinupgames.pusher

import com.google.gson.annotations.SerializedName
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

data class RegisterRequestBody(
    val gadid: String,
    val country: String,
    val language: String,
    @SerializedName("app_package") val appPackage: String
)

data class UpdateTokenRequestBody(
    val fcmtoken: String,
    @SerializedName("app_package") val appPackage: String
)

data class UpdateTagRequestBody(
    val tag: String,
    @SerializedName("app_package") val appPackage: String
)

data class SendNotificationBody(
    val gadid: String,
    @SerializedName("app_package") val appPackage: String,
    val title: String,
    val body: String
)

data class Response(
    val status: String
)

interface FCMPushServiceApi {

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("adduser")
    suspend fun register(@Body requestBody: RegisterRequestBody): Response

    @Headers("Content-Type: application/json;charset=UTF-8")
    @PUT("updatetoken/{gadid}")
    suspend fun updateToken(
        @Body requestBody: UpdateTokenRequestBody,
        @Path("gadid") gadid: String
    ): Response

    @Headers("Content-Type: application/json;charset=UTF-8")
    @PUT("updatetag/{gadid}")
    suspend fun updateTag(
        @Body updateTagRequestBody: UpdateTagRequestBody,
        @Path("gadid") gadid: String
    ): Response

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("sendNotification")
    suspend fun sendNotification(@Body requestBody: SendNotificationBody)
}