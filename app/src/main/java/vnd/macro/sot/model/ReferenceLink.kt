package vnd.macro.sot.model

import com.google.gson.annotations.SerializedName

data class ReferenceLink(
    @SerializedName("title")
    val title: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("thumbnail")
    val thumbnail: String,
    @SerializedName("desc")
    val desc: String
)