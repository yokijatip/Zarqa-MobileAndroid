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
import com.gity.myzarqa.databinding.FragmentOTPBinding
import com.gity.myzarqa.helper.ResourceHelper
import com.gity.myzarqa.utils.CommonUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OTPFragment : Fragment() {

    private var _binding: FragmentOTPBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentOTPBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClickListener()
        setupObserver()
    }

    private fun setupClickListener() {
        binding.btnSubmit.setOnClickListener {
            val otp = binding.edtOtp.text.toString()
            if(validateInput(otp)){
                viewModel.checkOTP(otp)
            }
        }
        handleBackButton()
    }

    private fun setupObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.checkOTPState.collect { result ->
                    when (result) {
                        is ResourceHelper.Error -> {
                            CommonUtils.showLoading(false, binding.progressBar)
                            CommonUtils.showErrorSnackBar(binding.root, result.message.toString())
                        }
                        is ResourceHelper.Loading -> {
                            CommonUtils.showLoading(true, binding.progressBar)
                        }
                        is ResourceHelper.Success -> {
                            CommonUtils.showLoading(false, binding.progressBar)
                            // Navigate to New Password Fragment
                            navigateToNewPasswordFragment(result.data?.data?.tokenTemporary.toString())
                        }
                        null -> {
                            CommonUtils.showLoading(false, binding.progressBar)
                        }
                    }
                }
            }
        }
    }

    private fun validateInput(otp: String): Boolean {
        var isValid = true

        if (otp.isEmpty() || otp.length < 3) {
            binding.edtOtp.error = "OTP must be at least 4 characters"
            isValid = false
        }

        return isValid
    }

    private fun navigateToNewPasswordFragment(tokenTemporary: String){
        // Buat instance baru dari NewPasswordFragment
        val newPasswordFragment = NewPasswordFragment().apply {
            // Tambahkan bundle ke fragment
            arguments = Bundle().apply {
                putString("tokenTemporary", tokenTemporary)
            }
        }

        // Ganti fragment dengan instance yang sudah memiliki bundle
        (activity as? AuthActivity)?.replaceFragment(newPasswordFragment)
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