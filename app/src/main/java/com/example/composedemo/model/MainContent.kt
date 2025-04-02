package com.example.composedemo.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

class MainContent {
    @Keep
    data class ContentWrapper(
        @SerializedName("contentKey") val contentKey: String,
        @SerializedName("content") val content: String
    )

    @Keep
    data class Data(
        @SerializedName("contents") val contents: List<ContentWrapper>
    )

    @Keep
    data class Error2(
        @SerializedName("code") val code: String,
        @SerializedName("internal_message") val internalMessage: String,
        @SerializedName("moreInfo") val moreInfo: List<String>
    )

    @Keep
    data class Responses(
        @SerializedName("ack") val ack: String,
        @SerializedName("data") val data: Data,
        @SerializedName("error") val error: Error2
    )

    @Keep
    data class ApiResponse(
        @SerializedName("response") val response: Responses
    )
}