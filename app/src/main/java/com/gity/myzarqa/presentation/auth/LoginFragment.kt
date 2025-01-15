package com.gity.myzarqa.presentation.auth

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.gity.myzarqa.databinding.FragmentLoginBinding
import com.gity.myzarqa.helper.ResourceHelper
import com.gity.myzarqa.presentation.common.base.MainActivity
import com.gity.myzarqa.utils.CommonUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObserver()
        setupClickListener()

    }

    private fun setupClickListener() {
        binding.btnLogin.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()

            if (validateInput(email, password)) {
                viewModel.login(email, password)
            }
        }
        binding.tvSignUp.setOnClickListener {
            navigateToRegister()
        }
    }

    private fun setupObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginState.collect { result ->
                    when (result) {
                        is ResourceHelper.Success -> {
                            CommonUtils.showLoading(false, binding.progressBar)
                            // Navigate to Main Activity
                            navigateToMain()
                        }
                        is ResourceHelper.Error -> {
                            CommonUtils.showLoading(false, binding.progressBar)
//                            CommonUtils.showErrorSnackBar(result.message.toString(), requireContext())
                            CommonUtils.showErrorSnackBar(binding.root, result.message.toString())
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

    private fun validateInput(email: String, password: String): Boolean {
        var isValid = true

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.edtEmail.error = "Invalid email address"
            isValid = false
        }

        if (password.isEmpty() || password.length < 6) {
            binding.edtPassword.error = "Password must be at least 6 characters"
            isValid = false
        }

        return isValid
    }

    private fun navigateToMain() {
        val intent = Intent(requireActivity(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun navigateToRegister() {
        (activity as? AuthActivity)?.replaceFragment(RegisterFragment())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}