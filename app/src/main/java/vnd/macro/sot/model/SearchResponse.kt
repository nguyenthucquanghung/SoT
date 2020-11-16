package vnd.macro.sot.model

import com.google.gson.annotations.SerializedName

data class SearchResponse (
    @SerializedName("isFake")
    val isFake: Boolean,
    @SerializedName("referenceLinks")
    val referenceLinks: List<ReferenceLink>
)