package com.example.kotlin_signup

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface SignUpService {

    @Headers("Content-Type: application/json")
    @POST("signup")
    fun addUser(@Body userInfo: SignUpRequestBody): Call<SignUpResponseBody>
}