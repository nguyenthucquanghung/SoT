package vnd.macro.sot.model

import com.google.gson.annotations.SerializedName

data class DatabaseSearchRefLink (
    @SerializedName("claimBy")
    val claimBy: String,
    @SerializedName("text")
    val text: String,
    @SerializedName("image")
    val imageUri: String,
    @SerializedName("reviews")
    val reviews: ArrayList<Review> = ArrayList()
)