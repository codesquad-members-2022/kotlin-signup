package com.example.kotlin_signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.kotlin_signup.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.idTextInputEditText.hint = "영문, 소문자, 숫자, 특수기호 : 5 ~ 20자"
        binding.idTextInputEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.idTextInputEditText.hint = ""
            } else {
                binding.idTextInputEditText.hint = R.string.IdHint.toString()
            }
        }
    }
}