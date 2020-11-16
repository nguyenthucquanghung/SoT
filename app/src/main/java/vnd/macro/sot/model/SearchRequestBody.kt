package vnd.macro.sot.model

import com.google.gson.annotations.SerializedName

data class SearchRequestBody (
    @SerializedName("lengthParam")
    val lengthParam: Int,
    @SerializedName("sotSearch")
    val sotSearch: String
)