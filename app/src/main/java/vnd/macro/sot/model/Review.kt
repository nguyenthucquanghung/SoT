package vnd.macro.sot.model

import com.google.gson.annotations.SerializedName

data class Review (
    @SerializedName("ratingName")
    val ratingName: String,
    @SerializedName("textualRating")
    val textualRating: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("reviewDate")
    val reviewDate: String
)