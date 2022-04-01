package com.example.kotlinsignup.datasource

import com.example.kotlinsignup.Users
import retrofit2.Call
import retrofit2.Response

interface DataSource {

    suspend fun getUsers(): Users?
}