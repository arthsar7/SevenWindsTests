package com.template.sevenwindstests.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.template.sevenwindstests.R
import com.template.sevenwindstests.databinding.FragmentRegisterBinding
import com.template.sevenwindstests.model.AuthResultState
import kotlinx.coroutines.launch

class FragmentRegister: Fragment() {
    private val binding by lazy {
        FragmentRegisterBinding.inflate(layoutInflater)
    }
    private val viewModel: AuthViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.btnRegister.setOnClickListener {
            when {
                binding.etMail.text.toString().isEmpty() || binding.etPassword.text.toString().isEmpty() || binding.etRepeatPassword.text.toString().isEmpty() -> {
                    return@setOnClickListener
                }
                !binding.etMail.text.toString().contains("@") -> {
                    return@setOnClickListener
                }
                binding.etPassword.text.toString() == binding.etRepeatPassword.text.toString() -> {
                    viewModel.register(
                        binding.etMail.text.toString(),
                        binding.etPassword.text.toString()
                    )
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    is AuthResultState.Success -> {
                        findNavController()
                            .navigate(R.id.action_fragmentRegister_to_fragmentLogin)
                    }
                    AuthResultState.Error -> {
                        Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                    }
                    AuthResultState.Idle -> { }
                }
            }
        }
        return binding.root
    }
}