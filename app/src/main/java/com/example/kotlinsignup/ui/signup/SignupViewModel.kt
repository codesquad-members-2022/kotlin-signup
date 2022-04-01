package com.example.kotlinsignup.ui.signup

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinsignup.R
import com.example.kotlinsignup.User
import com.example.kotlinsignup.Users
import com.example.kotlinsignup.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SignupViewModel(
    private val repository: Repository
) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    private val _users = MutableLiveData<Users>()
    val users: LiveData<Users>
        get() = _users

    val id: MutableLiveData<String> = MutableLiveData()
    val idMessage: MutableLiveData<Int> = MutableLiveData()
    val idColor: MutableLiveData<Int> = MutableLiveData()

    val password: MutableLiveData<String> = MutableLiveData()
    val passwordMessage: MutableLiveData<Int> = MutableLiveData()
    val passwordColor: MutableLiveData<Int> = MutableLiveData()

    val confirmPassword: MutableLiveData<String> = MutableLiveData()
    val confirmPasswordMessage: MutableLiveData<Int> = MutableLiveData()
    val confirmPasswordColor: MutableLiveData<Int> = MutableLiveData()

    val name: MutableLiveData<String> = MutableLiveData()
    val nameMessage: MutableLiveData<Int> = MutableLiveData()
    val nameColor: MutableLiveData<Int> = MutableLiveData()

    val isEnable: MutableLiveData<Boolean> = MutableLiveData(false)
    var idFlag = false
    var passwordFlag = false
    var confirmPasswordFlag = false
    var nameFlag = false

    fun onClickEvent(id: String, password: String, name: String) {
        _user.value = User(id, password, name)
    }

    val nameWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(editable: Editable?) {
            editable ?: return
            if (editable.isNullOrBlank()) {
                nameMessage.value = R.string.name_fail_message
                nameColor.value = R.color.red
                nameFlag = false
            } else {
                nameMessage.value = R.string.name_success_message
                nameColor.value = R.color.green
                nameFlag = true
            }
            updateEnabled()
        }
    }

    val idWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(editable: Editable?) {
            editable ?: return
            val pattern = """^[0-9a-z-_]{5,20}$""".toRegex()
            idFlag = false
            when {
                !pattern.matches(editable) -> {
                    idMessage.value = R.string.id_formatting_message
                    idColor.value = R.color.red
                    updateEnabled()
                }
                else -> checkUser(editable)
            }
        }

        private fun checkUser(editable: Editable) {
            viewModelScope.launch {
                val userList = repository.getUsers()
                val id = userList?.find { editable.toString() == it }

                id?.let {
                    idMessage.value = R.string.id_already_message
                    idColor.value = R.color.red
                    updateEnabled()
                } ?: run {
                    idMessage.value = R.string.id_success_message
                    idColor.value = R.color.green
                    idFlag = true
                    updateEnabled()
                }
            }
        }
    }

    val passwordWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(editable: Editable?) {
            editable ?: return
            val pattern = """^[0-9a-zA-Z!@#$%]{8,16}$""".toRegex()
            val upperCaseLetterPattern = """[A-Z]+""".toRegex()
            val numberPattern = """[0-9]+""".toRegex()
            val specialPattern = """[!@#$%]+""".toRegex()
            val length = editable.toString().length
            passwordFlag = false

            if (length < 8 || length > 16) {
                passwordMessage.value = R.string.password_length_message
                passwordColor.value = R.color.red
            } else if (!upperCaseLetterPattern.containsMatchIn(editable)) {
                passwordMessage.value = R.string.password_uppercase_message
                passwordColor.value = R.color.red
            } else if (!numberPattern.containsMatchIn(editable)) {
                passwordMessage.value = R.string.password_number_message
                passwordColor.value = R.color.red
            } else if (!specialPattern.containsMatchIn(editable)) {
                passwordMessage.value = R.string.password_special_message
                passwordColor.value = R.color.red
            } else if (pattern.matches(editable)) {
                passwordFlag = true
                passwordMessage.value = R.string.password_success_message
                passwordColor.value = R.color.green
            }
            updateEnabled()
        }
    }

    val confirmWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(editable: Editable?) {
            if (passwordFlag && password.value == confirmPassword.value) {
                confirmPasswordMessage.value = R.string.confirm_password_success_message
                confirmPasswordColor.value = R.color.green
                confirmPasswordFlag = true
            } else {
                confirmPasswordMessage.value = R.string.confirm_password_fail_message
                confirmPasswordColor.value = R.color.red
                confirmPasswordFlag = false
            }
            updateEnabled()
        }

    }

    private fun updateEnabled() {
        isEnable.value = idFlag && passwordFlag && confirmPasswordFlag && nameFlag
    }

}
