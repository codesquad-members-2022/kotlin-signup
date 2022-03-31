package com.example.kotlin_signup

import com.google.gson.annotations.SerializedName

data class SignUpRequestBody(
    @SerializedName("id")
    val userId: String?,
    @SerializedName("password")
    val password: String?
)
