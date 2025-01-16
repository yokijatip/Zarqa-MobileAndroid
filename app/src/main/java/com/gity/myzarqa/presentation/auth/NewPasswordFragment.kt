package com.gity.myzarqa.presentation.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.gity.myzarqa.databinding.FragmentNewPasswordBinding
import com.gity.myzarqa.helper.ResourceHelper
import com.gity.myzarqa.utils.CommonUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewPasswordFragment : Fragment() {

    private var _binding: FragmentNewPasswordBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by viewModels()

    private var tokenTemporary: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentNewPasswordBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tokenTemporary = arguments?.getString("tokenTemporary")
        setupClickListener()
        setupObserver()
    }

    private fun setupClickListener() {
        binding.btnSubmit.setOnClickListener {
            val password = binding.edtPassword.text.toString()
//            Get Temporary token from bundle
            tokenTemporary?.let { token ->
                if (validateInput(password)) {
                    viewModel.newPassword(token, password)
                }
            } ?: run {
                CommonUtils.showErrorSnackBar(binding.root, "Token temporary is null")
                navigateToOTPFragment()
            }
        }
        binding.btnBack.setOnClickListener {
            handleBackButton()
        }
    }

    private fun setupObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.newPasswordState.collect { result ->
                    when (result) {
                        is ResourceHelper.Success -> {
                            CommonUtils.showLoading(false, binding.progressBar)
                            // Navigate to Login Fragment
                            navigateToLoginFragment()
                        }

                        is ResourceHelper.Error -> {
                            CommonUtils.showLoading(false, binding.progressBar)
                            CommonUtils.showErrorSnackBar(binding.root, result.message.toString())
                        }

                        is ResourceHelper.Loading -> {
                            CommonUtils.showLoading(true, binding.progressBar)
                        }

                        null -> {
                            CommonUtils.showLoading(false, binding.progressBar)
                        }
                    }
                }
            }
        }
    }

    private fun navigateToOTPFragment() {
        (activity as? AuthActivity)?.replaceFragment(OTPFragment())
    }

    private fun navigateToLoginFragment() {
        (activity as? AuthActivity)?.replaceFragment(LoginFragment())
    }

    private fun validateInput(password: String): Boolean {
        var isValid = true

        if (password.isEmpty() || password.length < 6) {
            binding.edtPassword.error = "Password must be at least 6 characters"
            isValid = false
        }

        return isValid
    }

    private fun handleBackButton() {
        navigateToOTPFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}