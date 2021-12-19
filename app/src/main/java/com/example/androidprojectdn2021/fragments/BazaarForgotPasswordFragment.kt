package com.example.androidprojectdn2021.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.androidprojectdn2021.databinding.FragmentBazaarForgotPasswordBinding
import com.example.androidprojectdn2021.repository.Repository
import com.example.androidprojectdn2021.viewmodels.ForgotPasswordViewModel
import com.example.androidprojectdn2021.viewmodels.ForgotPasswordViewModelFactory

class BazaarForgotPasswordFragment : Fragment() {
    private lateinit var binding: FragmentBazaarForgotPasswordBinding
    private lateinit var forgotPasswordViewModel: ForgotPasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = ForgotPasswordViewModelFactory(this.requireContext(), Repository())
        forgotPasswordViewModel =
            ViewModelProvider(this, factory).get(ForgotPasswordViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBazaarForgotPasswordBinding.inflate(layoutInflater)

        binding.emailMeButton.setOnClickListener {
            if (binding.usernameTextInputForgotPFragment.text?.isEmpty() == false
                && binding.emailTextInputForgotPFragment.text?.isEmpty() == false
            ) {
                forgotPasswordViewModel.userName =
                    binding.usernameTextInputForgotPFragment.text.toString()
                forgotPasswordViewModel.email =
                    binding.emailTextInputForgotPFragment.text.toString()

                lifecycleScope.launchWhenCreated {
                    forgotPasswordViewModel.resetPassword()
                }
            }
        }

        return binding.root
    }
}