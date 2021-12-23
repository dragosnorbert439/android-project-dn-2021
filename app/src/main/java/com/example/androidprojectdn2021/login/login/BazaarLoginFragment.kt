package com.example.androidprojectdn2021.login.login

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.androidprojectdn2021.market.MarketActivity
import com.example.androidprojectdn2021.R
import com.example.androidprojectdn2021.databinding.FragmentBazaarLoginFragmentBinding
import com.example.androidprojectdn2021.repository.Repository
import com.example.androidprojectdn2021.user.Token.token
import com.example.androidprojectdn2021.user.UserData.user

class BazaarLoginFragment : Fragment() {
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: FragmentBazaarLoginFragmentBinding
    private lateinit var builder: AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = LoginViewModelFactory(this.requireContext(), Repository())
        loginViewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)
        builder = AlertDialog.Builder(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBazaarLoginFragmentBinding.inflate(layoutInflater)
        token.value = ""
        user.value?.username = ""
        user.value?.email = ""
        user.value?.password = ""
        user.value?.phone_number = ""
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // LOGIN BUTTON
        setUpLoginButton()

        // SIGNUP BUTTON
        setUpSignUpButton()

        // OBSERVE THE TOKEN
        setUpTokenObserver()

        // OBSERVE THE RESULT CODE - to inform the user in some way
        setUpResultCodeObserver()

        // CLICK HERE - FORGOT PASSWORD
        setUpForgotPasswordTV()
    }

    private fun setUpLoginButton() {
        binding.loginFragmentLogInButton.setOnClickListener {
            user.value.let {
                if (binding.loginFragmentUsernameTE.text?.isEmpty() == true) {
                    Toast.makeText(context, "Username field empty!", Toast.LENGTH_LONG).show()
                } else if (binding.loginFragmentPasswordTE.text?.isEmpty() == true) {
                    Toast.makeText(context, "Password field empty!", Toast.LENGTH_LONG).show()
                } else {
                    if (it != null) {
                        it.username = binding.loginFragmentUsernameTE.text.toString()
                    }
                    if (it != null) {
                        it.password = binding.loginFragmentPasswordTE.text.toString()
                    }
                }
            }
            lifecycleScope.launchWhenCreated {
                loginViewModel.login()
            }
        }
    }

    private fun setUpSignUpButton() {
        binding.signUpButton.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_bazaarLoginFragment_to_bazaarRegisterFragment)
        }
    }

    private fun setUpTokenObserver() {
        token.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                Log.d("dnj", "Navigate to MarketActivity")
                val intent = Intent(activity, MarketActivity::class.java)
                intent.putExtra("token", token.value)
                intent.putExtra("username", user.value?.username)
                intent.putExtra("email", user.value?.email)
                intent.putExtra("phone_number", user.value?.phone_number)
                Log.d("dnj", "Login fragment: ${user.value}")
                activity?.startActivity(intent)
            }
        }
    }

    private fun setUpResultCodeObserver() {
        loginViewModel.code.observe(viewLifecycleOwner) {
            when (loginViewModel.code.value) {
                300 -> {
                    Toast.makeText(
                        requireContext(),
                        "Username or password missing.",
                        Toast.LENGTH_LONG
                    ).show()
                }
                301 -> {
                    Toast.makeText(requireContext(), "Account not activated.", Toast.LENGTH_LONG)
                        .show()
                }
                302 -> {
                    Toast.makeText(requireContext(), "Incorrect username or password.", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun setUpForgotPasswordTV() {
        binding.clickHereTextView.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_bazaarLoginFragment_to_bazaar_forgot_password)
        }
    }
}