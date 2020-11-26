package vnd.macro.sot.model

import com.google.gson.annotations.SerializedName

data class SelectRequestBody (
    @SerializedName("lengthParam")
    val lengthParam: Int,
    @SerializedName("sotSelect")
    val sotSearch: String?
)