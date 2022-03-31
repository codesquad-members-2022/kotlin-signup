package com.example.kotlin_signup

import com.google.gson.annotations.SerializedName

data class SignUpResponseBody(
    @SerializedName("result")
    val result: String?,
    @SerializedName("status")
    val status: String?
)