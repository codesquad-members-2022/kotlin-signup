package com.example.kotlin_signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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

        binding.passwordRecheckTextInputEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.passwordRecheckTextInputLayout.editText?.addTextChangedListener(listener)
            }
        }
    }

    private val listener = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            if (s != null) {
                if (binding.passwordRecheckTextInputLayout.editText?.text.toString() != binding.passwordTextInputLayout.editText?.text.toString()) {
                    binding.passwordRecheckTextInputLayout.error = "비밀번호가 일치하지 않습니다"
                } else {
                    binding.passwordRecheckTextInputLayout.error = null
                }
            }
        }
    }
}