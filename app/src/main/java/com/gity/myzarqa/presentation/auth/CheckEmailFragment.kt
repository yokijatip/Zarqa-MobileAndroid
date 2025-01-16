package com.gity.myzarqa.presentation.auth

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
import com.gity.myzarqa.databinding.FragmentCheckEmailBinding
import com.gity.myzarqa.helper.ResourceHelper
import com.gity.myzarqa.utils.CommonUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class CheckEmailFragment : Fragment() {

    private var _binding: FragmentCheckEmailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCheckEmailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListener()
        setupObserver()
    }

    private fun setupClickListener() {
        binding.btnSubmit.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            if(validateInput(email)){
                viewModel.checkEmail(email)
            }
        }
        handleBackButton()
    }

    private fun setupObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.checkEmailState.collect { result ->
                    when (result) {
                        is ResourceHelper.Error -> {
                            CommonUtils.showLoading(false, binding.progressBar)
                            Timber.e(result.message)
                            CommonUtils.showErrorSnackBar(binding.root, result.message.toString())
                        }
                        is ResourceHelper.Loading -> {
                            CommonUtils.showLoading(true, binding.progressBar)
                        }
                        is ResourceHelper.Success -> {
                            CommonUtils.showLoading(false, binding.progressBar)
                            navigateToOTPFragment()
                        }
                        null -> {
                            CommonUtils.showLoading(false, binding.progressBar)
                        }
                    }
                }
            }
        }
    }

    private fun validateInput(email: String): Boolean {
        var isValid = true

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.edtEmail.error = "Invalid email address"
            isValid = false
        }

        return isValid
    }

    private fun navigateToOTPFragment(){
        (activity as? AuthActivity)?.replaceFragment(OTPFragment())
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