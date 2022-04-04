package com.example.kotlinsignup.repository

import com.example.kotlinsignup.Users
import com.example.kotlinsignup.datasource.UserDataSource
import retrofit2.Call

class Repository(
    private val userDataSource: UserDataSource
) {

    suspend fun getUsers(): Users? {
        return userDataSource.getUsers()
    }
}