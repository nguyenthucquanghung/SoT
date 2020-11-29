package vnd.macro.sot.model

import com.google.gson.annotations.SerializedName

data class LoginResponse (
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("tokenType")
    val tokenType: String,
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("expiresInSeconds")
    val expiresTime: Int,
    @SerializedName("system")
    val system: String
)