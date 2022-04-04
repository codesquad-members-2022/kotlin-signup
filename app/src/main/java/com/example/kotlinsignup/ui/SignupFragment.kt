package com.example.kotlinsignup.ui

import android.graphics.Color
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import com.example.kotlinsignup.R

class SignupFragment : Fragment() {

    private lateinit var passwordImageView: ImageView
    private lateinit var confirmPasswordImageView: ImageView
    private lateinit var passwordMessageTextView: TextView
    private lateinit var confirmPasswordMessageTextView: TextView
    private lateinit var nameMessageTextView: TextView
    private lateinit var idMessageTextView: TextView
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var nameEditText: EditText
    private lateinit var idEditText: EditText
    private lateinit var nextButton: Button
    private var passwordShowFlag = false
    private var confirmPasswordShowFlag = false
    private var idFlag = false
    private var passwordFlag = false
    private var confirmPasswordFlag = false
    private var nameFlag = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        init(view)
        passwordToggle()
        idEvent()
        passwordEvent()
        confirmPasswordEvent()
        nameEvent()
        nextButton.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.container_main, PersonalInformation()).commit()
        }
    }

    private fun buttonEnable() {
        nextButton.isEnabled = idFlag && passwordFlag && confirmPasswordFlag && nameFlag
    }

    private fun init(view: View) {
        idMessageTextView = view.findViewById(R.id.tv_id_message)
        passwordMessageTextView = view.findViewById(R.id.tv_password_message)
        confirmPasswordMessageTextView = view.findViewById(R.id.tv_confirm_password_message)
        nameMessageTextView = view.findViewById(R.id.tv_name_message)
        nameEditText = view.findViewById(R.id.edittext_name)
        passwordImageView = view.findViewById(R.id.iv_password)
        confirmPasswordImageView = view.findViewById(R.id.iv_confirm_password)
        passwordEditText = view.findViewById(R.id.edittext_password)
        confirmPasswordEditText = view.findViewById(R.id.edittext_confirm_password)
        idEditText = view.findViewById(R.id.edittext_id)
        nextButton = view.findViewById(R.id.btn_next)
    }

    private fun nameEvent() {
        nameEditText.addTextChangedListener { editable ->
            editable ?: return@addTextChangedListener
            if (editable.isNullOrBlank()) {
                nameMessageTextView.text = getString(R.string.name_fail_message)
                nameMessageTextView.setTextColor(Color.RED)
                nameFlag = false
            } else {
                nameMessageTextView.text = getString(R.string.name_success_message)
                nameMessageTextView.setTextColor(Color.GREEN)
                nameFlag = true
            }
            buttonEnable()
        }
    }

    private fun confirmPasswordEvent() {
        confirmPasswordEditText.addTextChangedListener { editable ->
            editable ?: return@addTextChangedListener
            if (passwordFlag && (passwordEditText.text.toString() == confirmPasswordEditText.text.toString())) {
                confirmPasswordMessageTextView.text =
                    getString(R.string.confirm_password_success_message)
                confirmPasswordMessageTextView.setTextColor(Color.GREEN)
                confirmPasswordFlag = true
            } else {
                confirmPasswordMessageTextView.text =
                    getString(R.string.confirm_password_fail_message)
                confirmPasswordMessageTextView.setTextColor(Color.RED)
                confirmPasswordFlag = false
            }
            buttonEnable()
        }
    }

    private fun passwordEvent() {
        passwordEditText.addTextChangedListener { editable ->
            editable ?: return@addTextChangedListener
            val pattern = """^[0-9a-zA-Z!@#$%]{8,16}$""".toRegex()
            val upperCaseLetterPattern = """[A-Z]+""".toRegex()
            val numberPattern = """[0-9]+""".toRegex()
            val specialPattern = """[!@#$%]+""".toRegex()
            val length = editable.toString().length
            passwordFlag = false

            if (length < 8 || length > 16) {
                passwordMessageTextView.text = getString(R.string.password_length_message)
                passwordMessageTextView.setTextColor(Color.RED)
            } else if (!upperCaseLetterPattern.containsMatchIn(editable)) {
                passwordMessageTextView.text = getString(R.string.password_uppercase_message)
                passwordMessageTextView.setTextColor(Color.RED)
            } else if (!numberPattern.containsMatchIn(editable)) {
                passwordMessageTextView.text = getString(R.string.password_number_message)
                passwordMessageTextView.setTextColor(Color.RED)
            } else if (!specialPattern.containsMatchIn(editable)) {
                passwordMessageTextView.text = getString(R.string.password_special_message)
                passwordMessageTextView.setTextColor(Color.RED)
            } else if (pattern.matches(editable)) {
                passwordFlag = true
                passwordMessageTextView.text = getString(R.string.password_success_message)
                passwordMessageTextView.setTextColor(Color.GREEN)
            }
            buttonEnable()
        }
    }

    private fun idEvent() {
        idEditText.addTextChangedListener { editable ->
            editable ?: return@addTextChangedListener
            val pattern = """^[0-9a-z-_]{5,20}$""".toRegex()
            idFlag = false
            if (!pattern.matches(editable)) {
                idMessageTextView.text = getString(R.string.id_formatting_message)
                idMessageTextView.setTextColor(Color.RED)
            } else {
                idFlag = true
                idMessageTextView.text = getString(R.string.id_success_message)
                idMessageTextView.setTextColor(Color.GREEN)
            }
            buttonEnable()
        }
    }

    private fun passwordToggle() {
        passwordImageView.setOnClickListener {
            passwordShowFlag = !passwordShowFlag
            showPassword(passwordShowFlag, passwordEditText, passwordImageView)
        }

        confirmPasswordImageView.setOnClickListener {
            confirmPasswordShowFlag = !confirmPasswordShowFlag
            showPassword(confirmPasswordShowFlag, confirmPasswordEditText, confirmPasswordImageView)
        }
    }

    private fun showPassword(isShow: Boolean, editText: EditText, imageView: ImageView) {
        when {
            isShow -> {
                editText.transformationMethod = PasswordTransformationMethod.getInstance()
                imageView.setImageResource(R.drawable.ic_baseline_remove_red_eye_24)
            }
            else -> {
                editText.transformationMethod = HideReturnsTransformationMethod.getInstance()
                imageView.setImageResource(R.drawable.ic_baseline_visibility_off_24)
            }
        }
        editText.setSelection(editText.text.toString().length)
    }
}