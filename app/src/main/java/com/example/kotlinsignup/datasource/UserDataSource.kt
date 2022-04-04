package com.example.kotlinsignup.datasource

import com.example.kotlinsignup.RetrofitApi
import com.example.kotlinsignup.Users

class UserDataSource: DataSource {
    override suspend fun getUsers(): Users? {
        val response = RetrofitApi.userService.getUsers()
        if(response.isSuccessful) {
            return response.body()
        }
        return null
    }
}