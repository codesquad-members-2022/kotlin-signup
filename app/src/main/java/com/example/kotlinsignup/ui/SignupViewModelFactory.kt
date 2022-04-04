package com.example.kotlinsignup.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kotlinsignup.datasource.UserDataSource
import com.example.kotlinsignup.repository.Repository
import com.example.kotlinsignup.ui.signup.SignupViewModel

class SignupViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SignupViewModel::class.java) -> {
                SignupViewModel(Repository(UserDataSource())) as T
            }
            else -> throw IllegalAccessException("cannot find viewModel")
        }
    }

}