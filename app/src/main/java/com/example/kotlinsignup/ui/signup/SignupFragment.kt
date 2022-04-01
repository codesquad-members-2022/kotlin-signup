package com.example.kotlinsignup.ui.signup

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.kotlinsignup.R
import com.example.kotlinsignup.Users
import com.example.kotlinsignup.databinding.FragmentSignupBinding
import com.example.kotlinsignup.ui.PersonalInformation
import com.example.kotlinsignup.ui.SignupViewModelFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupFragment : Fragment() {

    private var passwordShowFlag = false
    private var confirmPasswordShowFlag = false
    private val viewModel: SignupViewModel by viewModels { SignupViewModelFactory() }
    private lateinit var binding: FragmentSignupBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signup, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        passwordToggle()

        with(viewModel) {
            nameMessage.observe(viewLifecycleOwner) {
                binding.tvNameMessage.text = getString(it)
                nameColor.observe(viewLifecycleOwner) { color ->
                    binding.tvNameMessage.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            color
                        )
                    )
                }
            }

            idMessage.observe(viewLifecycleOwner) {
                binding.tvIdMessage.text = getString(it)
                idColor.observe(viewLifecycleOwner) { color ->
                    binding.tvIdMessage.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            color
                        )
                    )
                }
            }

            passwordMessage.observe(viewLifecycleOwner) {
                binding.tvPasswordMessage.text = getString(it)
                passwordColor.observe(viewLifecycleOwner) { color ->
                    binding.tvPasswordMessage.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            color
                        )
                    )
                }
            }

            confirmPasswordMessage.observe(viewLifecycleOwner) {
                binding.tvConfirmPasswordMessage.text = getString(it)
                confirmPasswordColor.observe(viewLifecycleOwner) { color ->
                    binding.tvConfirmPasswordMessage.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            color
                        )
                    )
                }
            }
        }

        viewModel.user.observe(viewLifecycleOwner) {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.container_main, PersonalInformation()).commit()
        }
    }

    private fun passwordToggle() {
        binding.ivPassword.setOnClickListener {
            passwordShowFlag = !passwordShowFlag
            showPassword(passwordShowFlag, binding.edittextPassword, binding.ivPassword)
        }

        binding.ivConfirmPassword.setOnClickListener {
            confirmPasswordShowFlag = !confirmPasswordShowFlag
            showPassword(
                confirmPasswordShowFlag,
                binding.edittextConfirmPassword,
                binding.ivConfirmPassword
            )
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