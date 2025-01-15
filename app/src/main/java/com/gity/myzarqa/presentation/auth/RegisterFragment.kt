package com.gity.myzarqa.presentation.auth

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.gity.myzarqa.databinding.FragmentRegisterBinding
import com.gity.myzarqa.helper.ResourceHelper
import com.gity.myzarqa.utils.CommonUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupValidateInputUserListener()
        setupObserver()
    }


    private fun setupValidateInputUserListener() {
        binding.btnSignUp.setOnClickListener {
            val fullname = binding.edtFullname.text.toString()
            val email = binding.edtEmail.text.toString().trim()
            val password = binding.edtPassword.text.toString().trim()
            val confirmPassword = binding.edtConfirmationPassword.text.toString().trim()

            if (validateInput(fullname, email, password, confirmPassword)) {
                // Jika validasi berhasil, lakukan registrasi viewModel.register(fullname, phone, email, password)
                viewModel.register(fullname, email, password)
            }
        }
        handleBackButton()
        navigateToLogin()
    }

    private fun setupObserver() {
        CommonUtils.showLoading(false, binding.progressBar)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.registerState.collect { result ->
                    when (result) {
                        is ResourceHelper.Success -> {
                            CommonUtils.showLoading(false, binding.progressBar)
                            CommonUtils.showSuccessSnackBar(binding.root, "Registrasi berhasil, Silahkan cek Email untuk verifikasi")
                            // Navigate to Login
                            (activity as? AuthActivity)?.replaceFragment(LoginFragment())

                        }
                        is ResourceHelper.Error -> {
                            CommonUtils.showLoading(false, binding.progressBar)
                            CommonUtils.showErrorSnackBar(binding.root, result.message.toString(), "Ok")
                        }
                        is ResourceHelper.Loading -> {
                            CommonUtils.showLoading(true, binding.progressBar)
                        }

                        null -> {
                            // Initial state, tidak perlu melakukan apa-apa
                            CommonUtils.showLoading(false, binding.progressBar)
                        }
                    }
                }
            }
        }
    }

    private fun validateInput(
        fullname: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        when {
            fullname.isEmpty() -> {
                CommonUtils.showSnackBar(binding.root, "Nama lengkap harus diisi")
                return false
            }

            email.isEmpty() -> {
                CommonUtils.showSnackBar(binding.root, "Email harus diisi")
                return false
            }

            password.isEmpty() -> {
                CommonUtils.showSnackBar(binding.root, "Password harus diisi")
                return false
            }

            confirmPassword.isEmpty() -> {
                CommonUtils.showSnackBar(binding.root, "Konfirmasi password harus diisi")
                return false
            }

            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                CommonUtils.showSnackBar(binding.root, "Format email tidak valid")
                return false
            }

            password.length < 6 -> {
                CommonUtils.showSnackBar(binding.root, "Password minimal 6 karakter")
                return false
            }

            password != confirmPassword -> {
                CommonUtils.showSnackBar(binding.root, "Password dan konfirmasi password tidak sama")
                return false
            }

            else -> return true
        }
    }

    private fun navigateToLogin() {
        binding.tvSignIn.setOnClickListener {
            (activity as? AuthActivity)?.replaceFragment(LoginFragment())
        }
    }


    private fun handleBackButton() {
        binding.btnBack.setOnClickListener {
            (activity as? AuthActivity)?.replaceFragment(LoginFragment())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}