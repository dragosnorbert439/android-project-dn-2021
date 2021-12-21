package com.example.androidprojectdn2021.fragments

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
import com.example.androidprojectdn2021.MarketActivity
import com.example.androidprojectdn2021.R
import com.example.androidprojectdn2021.databinding.FragmentBazaarLoginFragmentBinding
import com.example.androidprojectdn2021.repository.Repository
import com.example.androidprojectdn2021.user.Token.token
import com.example.androidprojectdn2021.user.UserData.user
import com.example.androidprojectdn2021.viewmodels.LoginViewModel
import com.example.androidprojectdn2021.viewmodels.LoginViewModelFactory

class BazaarLoginFragment : Fragment() {
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: FragmentBazaarLoginFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = LoginViewModelFactory(this.requireContext(), Repository())
        loginViewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBazaarLoginFragmentBinding.inflate(layoutInflater)

        // LOGIN BUTTON
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

        // SIGNUP BUTTON
        binding.signUpButton.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_bazaarLoginFragment_to_bazaarRegisterFragment)
        }

        // OBSERVE THE TOKEN
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

        // CLICK HERE - FORGOT PASSWORD
        binding.clickHereTextView.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_bazaarLoginFragment_to_bazaar_forgot_password)
        }

        return binding.root
    }
}