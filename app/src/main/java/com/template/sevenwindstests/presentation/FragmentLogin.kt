package com.template.sevenwindstests.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.template.sevenwindstests.R
import com.template.sevenwindstests.databinding.FragmentLoginBinding
import com.template.sevenwindstests.model.AuthResultState
import kotlinx.coroutines.launch

class FragmentLogin : Fragment() {
    private val binding by lazy {
        FragmentLoginBinding.inflate(layoutInflater)
    }
    private val viewModel: AuthViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.btnRegister.setOnClickListener {
            if (binding.etMail.text.toString().isEmpty() || binding.etPassword.text.toString()
                    .isEmpty()
            ) {
                return@setOnClickListener
            }
            else if (!binding.etMail.text.toString().contains("@")) {
                return@setOnClickListener
            }
            viewModel.login(
                binding.etMail.text.toString(),
                binding.etPassword.text.toString()
            )
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    AuthResultState.Error -> {
                        Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                    }
                    AuthResultState.Idle -> { }
                    is AuthResultState.Success -> {
                        val data = it.response
                        Log.d("DATA", data.toString())
                        findNavController()
                            .navigate(R.id.action_fragmentLogin_to_fragmentCoffeeLocations)
                    }
                }
            }
        }
        return binding.root
    }
}