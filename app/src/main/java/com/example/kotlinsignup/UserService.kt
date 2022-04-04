package com.example.kotlinsignup

import retrofit2.Response
import retrofit2.http.GET

interface UserService {

    @GET("/signup")
    suspend fun getUsers(): Response<Users>
}