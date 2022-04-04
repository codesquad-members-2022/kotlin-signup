package com.example.kotlin_signup

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import com.example.kotlin_signup.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private var idFlag = false
    private var passwordFlag = false
    private var passwordCheckFlag = false
    private var nameFlag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.nextButton.isEnabled = false

        binding.idTextInputLayout.editText?.addTextChangedListener(idListener)
        binding.idTextInputEditText.hint = resources.getString(R.string.id_hint)
        binding.idTextInputEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.idTextInputEditText.hint = ""
            } else {
                binding.idTextInputEditText.hint = resources.getString(R.string.id_hint)
            }
        }

        binding.passwordTextInputLayout.editText?.addTextChangedListener(passwordListener)
        binding.passwordTextInputEditText.hint = resources.getString(R.string.password_hint)
        binding.passwordTextInputEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.passwordTextInputEditText.hint = ""
            } else {
                binding.passwordTextInputEditText.hint =
                    resources.getString(R.string.password_hint)
            }
        }

        binding.passwordRecheckTextInputLayout.editText?.addTextChangedListener(
            passwordRecheckListener
        )

        binding.nameTextviewInputLayout.editText?.addTextChangedListener(nameListener)
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
                        idFlag = false
                    }
                    !idRegex(s.toString()) -> {
                        binding.idTextInputLayout.error = "아이디 양식이 맞지 않습니다"
                        idFlag = false
                    }
                    else -> {
                        binding.idTextInputLayout.error = null
                        idFlag = true
                    }
                }
                flagCheck()
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
                        passwordFlag = false
                    }
                    !passwordRegex(s.toString()) -> {
                        binding.passwordTextInputLayout.error = "비밀번호 양식이 일치하지 않습니다."
                        passwordFlag = false
                    }
                    else -> {
                        binding.passwordTextInputLayout.error = null
                        passwordFlag = true
                    }
                }
                flagCheck()
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
                        passwordCheckFlag = false
                    }
                    binding.passwordRecheckTextInputLayout.editText?.text.toString() != binding.passwordTextInputLayout.editText?.text.toString() -> {
                        binding.passwordRecheckTextInputLayout.error = "비밀번호가 일치하지 않습니다"
                        passwordCheckFlag = false
                    }
                    else -> {
                        binding.passwordRecheckTextInputLayout.error = null
                        passwordCheckFlag = true
                    }
                }
                flagCheck()
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
                        binding.nameTextviewInputLayout.error = "이름은 필수 입력 항목입니다."
                        nameFlag = false
                    }
                    else -> {
                        binding.nameTextviewInputLayout.error = null
                        nameFlag = true
                    }
                }
                flagCheck()
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

    fun flagCheck() {
        binding.nextButton.isEnabled = idFlag && passwordFlag && passwordCheckFlag && nameFlag
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val imm: InputMethodManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        return true
    }
}