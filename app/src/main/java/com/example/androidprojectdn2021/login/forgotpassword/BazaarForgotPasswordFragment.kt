package com.example.androidprojectdn2021.login.forgotpassword

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.androidprojectdn2021.R
import com.example.androidprojectdn2021.databinding.FragmentBazaarForgotPasswordBinding
import com.example.androidprojectdn2021.repository.Repository

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
    ): View {
        binding = FragmentBazaarForgotPasswordBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // EMAIL ME BUTTON
        setUpEmailMeButton()

        // SET UP OBSERVER ON RESULT CODE
        setUpObserverResultCode()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback = object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    private fun setUpObserverResultCode() {
        forgotPasswordViewModel.code.observe(viewLifecycleOwner) {
            when (forgotPasswordViewModel.code.value) {
                200 -> {
                    Toast.makeText(activity, "Password reset successfully.", Toast.LENGTH_LONG)
                        .show()
                    Navigation.findNavController(binding.root)
                        .navigate(R.id.action_bazaarForgotPassword_to_bazaarLoginFragment)
                }
                300 -> {
                    Log.d(
                        "dnj", "Username or email not set correctly."
                    )
                    Toast.makeText(
                        activity, "Username or email not set correctly.",
                        Toast.LENGTH_LONG
                    ).show()
                }
                301 -> {
                    Log.d("dnj", "Username or email is wrong.")
                    Toast.makeText(
                        activity, "Username or email is wrong.",
                        Toast.LENGTH_LONG
                    ).show()
                }
                302 -> {
                    Log.d("dnj", "Mail could not be sent")
                    Toast.makeText(
                        activity, "Mail could not be sent",
                        Toast.LENGTH_LONG
                    ).show()
                }
                else -> {
                    Log.d("dnj", "Unexpected error.")
                    Toast.makeText(
                        activity, "Unexpected error.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun setUpEmailMeButton() {
        binding.emailMeButton.setOnClickListener {
            forgotPasswordViewModel.userName =
                binding.usernameTextInputForgotPFragment.text.toString()
            forgotPasswordViewModel.email =
                binding.emailTextInputForgotPFragment.text.toString()
            lifecycleScope.launchWhenCreated {
                forgotPasswordViewModel.resetPassword()
            }
        }
    }
}