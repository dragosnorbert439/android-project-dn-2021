package com.example.androidprojectdn2021.login.register

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
import com.example.androidprojectdn2021.databinding.FragmentBazaarRegisterBinding
import com.example.androidprojectdn2021.repository.Repository
import com.example.androidprojectdn2021.user.UserData.user

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
    ): View {
        binding = FragmentBazaarRegisterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // LOGIN TEXTVIEW
        setUpLoginTextView()

        // REGISTER BUTTON
        setUpRegisterButton()

        // OBSERVER ON RESPONSE CODE
        setUpObserverOnResponseCode()
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

    private fun setUpLoginTextView() {
        binding.registerFragmentLoginTV.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_bazaarRegisterFragment_to_bazaarLoginFragment)
        }
    }

    private fun setUpRegisterButton() {
        binding.registerFragmentRegisterButton.setOnClickListener {
            user.value.let {
                if (it != null) {
                    it.username = binding.registerFragmentUsernameTE.text.toString()
                    it.password = binding.registerFragmentPasswordTE.text.toString()
                    it.phone_number = binding.registerFragmentPhoneTE.text.toString()
                    it.email = binding.registerFragmentEmailTE.text.toString()
                }
            }
            lifecycleScope.launchWhenCreated {
                registerViewModel.register()
            }
        }
    }

    private fun setUpObserverOnResponseCode() {
        registerViewModel.code.observe(viewLifecycleOwner) {
            when (registerViewModel.code.value) {
                200 -> {
                    Toast.makeText(activity, "Creation successful.", Toast.LENGTH_LONG).show()
                    Navigation.findNavController(binding.root)
                        .navigate(R.id.action_bazaarRegisterFragment_to_bazaarLoginFragment)
                }
                300 -> {
                    Log.d(
                        "dnj", "One of the following: user name, password,\n" +
                                "email, phone number are either empty or missing."
                    )
                    Toast.makeText(
                        activity, "One of the following: user name, password,\n" +
                                "email, phone number are either empty or missing.",
                        Toast.LENGTH_LONG
                    ).show()
                }
                301 -> {
                    Log.d("dnj", "Wrong file format.\n Only jpeg or png are allowed.")
                    Toast.makeText(
                        activity, "Wrong file format.\n Only jpeg or png are allowed.",
                        Toast.LENGTH_LONG
                    ).show()
                }
                302 -> {
                    Log.d("dnj", "Email incorrect or empty.\n You need to enter another email.\n")
                    Toast.makeText(
                        activity, "Email incorrect or empty.\n You need to enter another email.\n",
                        Toast.LENGTH_LONG
                    ).show()
                }
                303 -> {
                    Log.d("dnj", "Username, email or phone number already used.")
                    Toast.makeText(
                        activity, "Username, email or phone number already used.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}