package com.example.kotlin_signup

import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface SignUpService {

    @Headers("Content-Type: application/json")
    @POST("signup")
    suspend fun addUser(@Body userInfo: RequestBody): Response<SignUpResponseBody>
}