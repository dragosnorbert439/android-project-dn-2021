package com.example.androidprojectdn2021.fragments

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
import com.example.androidprojectdn2021.R
import com.example.androidprojectdn2021.databinding.FragmentBazaarLoginFragmentBinding
import com.example.androidprojectdn2021.databinding.FragmentBazaarRegisterBinding
import com.example.androidprojectdn2021.repository.Repository
import com.example.androidprojectdn2021.viewmodels.LoginViewModel
import com.example.androidprojectdn2021.viewmodels.RegisterViewModel
import com.example.androidprojectdn2021.viewmodels.RegisterViewModelFactory

class BazaarRegisterFragment : Fragment() {
    private lateinit var binding: FragmentBazaarRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = RegisterViewModelFactory(this.requireContext(), Repository())
        registerViewModel = ViewModelProvider(this, factory).get(RegisterViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBazaarRegisterBinding.inflate(layoutInflater)

        // LOGIN TEXTVIEW
        binding.registerFragmentLoginTV.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_bazaarRegisterFragment_to_bazaarLoginFragment)
        }

        // REGISTER BUTTON
        binding.registerFragmentRegisterButton.setOnClickListener {
            registerViewModel.user.value.let {
                if (binding.registerFragmentUsernameTE.text?.isEmpty() == true) {
                    Toast.makeText(context,"Username field empty!", Toast.LENGTH_LONG).show()
                }
                else if (binding.registerFragmentPasswordTE.text?.isEmpty() == true) {
                    Toast.makeText(context,"Password field empty!", Toast.LENGTH_LONG).show()
                }
                else if (binding.registerFragmentPhoneTE.text?.isEmpty() == true) {
                    Toast.makeText(context,"Phone number field empty!", Toast.LENGTH_LONG).show()
                }
                else if (binding.registerFragmentEmailTE.text?.isEmpty() == true) {
                    Toast.makeText(context,"Email field empty!", Toast.LENGTH_LONG).show()
                }
                else {
                    if (it != null) {
                        it.username = binding.registerFragmentUsernameTE.text.toString()
                    }
                    if (it != null) {
                        it.password = binding.registerFragmentPasswordTE.text.toString()
                    }
                    if (it != null) {
                        it.phone_number = binding.registerFragmentPhoneTE.text.toString()
                    }
                    if (it != null) {
                        it.email = binding.registerFragmentEmailTE.text.toString()
                    }
                }
            }
            lifecycleScope.launchWhenCreated {
                registerViewModel.register()
            }
        }
        registerViewModel.code.observe(viewLifecycleOwner){
            when (registerViewModel.code.value) {
                200 -> {
                    Log.d("dnj", "Navigate to Login")
                    Navigation.findNavController(binding.root).navigate(R.id.action_bazaarRegisterFragment_to_bazaarLoginFragment)
                    Toast.makeText(activity, "Creation successful.", Toast.LENGTH_LONG).show()
                }
                300 -> {
                    Log.d("dnj", "One of the following username, password,\n" +
                            "email, phone_number, userImage are either empty or missing.")
                    Toast.makeText(activity, "One of the following username , password ,\n" +
                            "email , phone_number, userImage are either empty or missing.",
                        Toast.LENGTH_LONG).show()
                }
                301 -> {
                    Log.d("dnj", "Wrong file format. Only jpeg or png are\n" +
                            "allowed.")
                    Toast.makeText(activity, "Wrong file format. Only jpeg or png are allowed.",
                        Toast.LENGTH_LONG).show()
                }
                302 -> {
                    Log.d("dnj", "Email incorrect. You need to enter another\n" +
                            "email.\n")
                    Toast.makeText(activity, "Email incorrect. You need to enter another email.",
                        Toast.LENGTH_LONG).show()
                }
                303 -> {
                    Log.d("dnj", "Username , email or phone number already used.")
                    Toast.makeText(activity, "Username , email or phone number already used.",
                        Toast.LENGTH_LONG).show()
                }
            }
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = BazaarRegisterFragment()
    }
}