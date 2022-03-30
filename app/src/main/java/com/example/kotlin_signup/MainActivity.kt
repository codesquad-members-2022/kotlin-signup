package com.example.kotlin_signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.example.kotlin_signup.databinding.ActivityMainBinding
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding) {
            idTextInputLayout.editText?.addTextChangedListener(idListener)
            idTextInputEditText.hint = resources.getString(R.string.id_hint)
            idTextInputEditText.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    binding.idTextInputEditText.hint = ""
                } else {
                    binding.idTextInputEditText.hint = resources.getString(R.string.id_hint)
                }
            }

            passwordTextInputLayout.editText?.addTextChangedListener(passwordListener)
            passwordTextInputEditText.hint = resources.getString(R.string.password_hint)
            passwordTextInputEditText.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    binding.passwordTextInputEditText.hint = ""
                } else {
                    binding.passwordTextInputEditText.hint =
                        resources.getString(R.string.password_hint)
                }
            }

            passwordRecheckTextInputLayout.editText?.addTextChangedListener(
                passwordRecheckListener
            )

            nameTextviewInputLayout.editText?.addTextChangedListener(nameListener)
        }
    }

    private val idListener = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            if (s != null) {
                when {
                    s.isEmpty() -> {
                        binding.idTextInputLayout.error = "아이디를 입력해주세요."
                        binding.nextButton.isEnabled = false
                    }
                    !idRegex(s.toString()) -> {
                        binding.idTextInputLayout.error = "아이디 양식이 맞지 않습니다"
                        binding.nextButton.isEnabled = false
                    }
                    else -> {
                        binding.passwordTextInputLayout.error = null
                    }
                }
            }
        }

    }

    private val passwordListener = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            if (s != null) {
                when {
                    s.isEmpty() -> {
                        binding.passwordTextInputLayout.error = "비밀번호를 입력해주세요."
                        binding.nextButton.isEnabled = false
                    }
                    !passwordRegex(s.toString()) -> {
                        binding.passwordTextInputLayout.error = "비밀번호 양식이 일치하지 않습니다."
                        binding.nextButton.isEnabled = false
                    }
                    else -> {
                        binding.passwordTextInputLayout.error = null
                    }
                }
            }
        }
    }

    private val passwordRecheckListener = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            if (s != null) {
                when {
                    s.isEmpty() -> {
                        binding.nextButton.isEnabled = false
                    }
                    binding.passwordRecheckTextInputLayout.editText?.text.toString() != binding.passwordTextInputLayout.editText?.text.toString() -> {
                        binding.passwordRecheckTextInputLayout.error = "비밀번호가 일치하지 않습니다"
                        binding.nextButton.isEnabled = false
                    }
                    else -> {
                        binding.passwordRecheckTextInputLayout.error = null
                    }
                }
            }
        }
    }

    private val nameListener = object  : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            if (s != null) {
                when {
                    s.isEmpty() -> {
                        binding.nextButton.isEnabled = false
                        binding.nameTextviewInputLayout.error = "이름은 필수 입력 항목입니다."
                    }
                    else -> {
                        binding.nameTextviewInputLayout.error = null
                    }
                }
            }
        }
    }

    // 특수문자 존재 여부를 확인하는 메서드
    private fun hasSpecialCharacter(string: String): Boolean {
        for (i in string.indices) {
            if (!Character.isLetterOrDigit(string[i])) {
                return true
            }
        }
        return false
    }

    // 영문자 존재 여부를 확인하는 메서드
    private fun hasAlphabet(string: String): Boolean {
        for (i in string.indices) {
            if (Character.isAlphabetic(string[i].code)) {
                return true
            }
        }
        return false
    }

    fun idRegex(id: String): Boolean {
        if ((!hasSpecialCharacter(id)) and (hasAlphabet(id))) {
            return true
        }
        return false
    }

    fun passwordRegex(password: String): Boolean {
        return password.matches("^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&.])[A-Za-z[0-9]$@$!%*#?&.]{8,16}$".toRegex())
    }
}