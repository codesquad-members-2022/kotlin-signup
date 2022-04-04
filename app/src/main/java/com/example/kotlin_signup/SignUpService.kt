package com.example.kotlin_signup

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface SignUpService {

    @Headers("Content-Type: application/json")
    @POST("signup")
    fun addUserByEnqueue(@Body userInfo: RequestBody): Call<SignUpResponseBody> // Call 은 흐름처리 기능을 제공해줌

    @Headers("Content-Type: application/json")
    @POST("signup")
    suspend fun addUser(@Body userInfo: RequestBody): Response<SignUpResponseBody> // 코루틴은 자체적으로 비동기적인 기능을 수행하기 때문에 Call 이 필요 없음
}