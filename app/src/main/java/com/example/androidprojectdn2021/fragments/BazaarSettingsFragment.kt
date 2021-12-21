package com.example.androidprojectdn2021.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.androidprojectdn2021.R
import com.example.androidprojectdn2021.modelclasses.User
import com.example.androidprojectdn2021.repository.Repository
import com.example.androidprojectdn2021.user.UserData.user
import com.example.androidprojectdn2021.viewmodels.MarketViewModel
import com.example.androidprojectdn2021.viewmodels.MarketViewModelFactory
import com.example.androidprojectdn2021.viewmodels.UserViewModel
import com.example.androidprojectdn2021.viewmodels.UserViewModelFactory


class BazaarSettingsFragment : Fragment() {
    lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userViewModelFactory = UserViewModelFactory(Repository())
        userViewModel = ViewModelProvider(this, userViewModelFactory).get(UserViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bazaar_settings, container, false)

        // BACK BUTTON
        val settingsBackButton = view.findViewById<AppCompatButton>(R.id.settingsBackButton)
        settingsBackButton.setOnClickListener {
            findNavController().popBackStack()
        }

        // FILL THE FIELDS
        view.findViewById<AppCompatTextView>(R.id.userNameDisplayTV).text =
            user.value?.username
        view.findViewById<AppCompatEditText>(R.id.userNameTE)
            .setText(user.value?.username)
        view.findViewById<AppCompatEditText>(R.id.emailTE).setText(user.value?.email)
        view.findViewById<AppCompatEditText>(R.id.phoneNumberTE)
            .setText(user.value?.phone_number)

        // PUBLISH BUTTON
        val publishButton = view.findViewById<AppCompatButton>(R.id.publishButtonSettings)
        publishButton.setOnClickListener {
            lifecycleScope.launchWhenCreated {
                userViewModel.updateUser(
                    User(
                        username = view.findViewById<AppCompatEditText>(R.id.userNameTE).text.toString(),
                        password = "",
                        email = view.findViewById<AppCompatEditText>(R.id.emailTE).text.toString(),
                        phone_number = view.findViewById<AppCompatEditText>(R.id.phoneNumberTE).text.toString()
                    )
                )
            }
            userViewModel.code.observe(viewLifecycleOwner) {
                if (userViewModel.code.value == 200) {

                }
            }
        }

        return view
    }
}