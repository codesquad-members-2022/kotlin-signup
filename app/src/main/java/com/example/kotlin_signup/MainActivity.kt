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

        binding.idTextInputEditText.hint = resources.getString(R.string.id_hint)
        binding.idTextInputEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.idTextInputEditText.hint = ""
            } else {
                binding.idTextInputEditText.hint = resources.getString(R.string.id_hint)
            }
        }

        binding.passwordTextInputEditText.hint = resources.getString(R.string.password_hint)
        binding.passwordTextInputEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.passwordTextInputEditText.hint = ""
            } else {
                binding.passwordTextInputEditText.hint = resources.getString(R.string.password_hint)
            }
        }
    }
}