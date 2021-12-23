package com.example.androidprojectdn2021.market.profile

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.AppCompatEditText
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.androidprojectdn2021.R
import com.example.androidprojectdn2021.databinding.FragmentBazaarSettingsBinding
import com.example.androidprojectdn2021.modelclasses.User
import com.example.androidprojectdn2021.repository.Repository
import com.example.androidprojectdn2021.user.UserData.user


class BazaarSettingsFragment : Fragment() {
    lateinit var userViewModel: UserViewModel
    private lateinit var binding: FragmentBazaarSettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userViewModelFactory = UserViewModelFactory(Repository())
        userViewModel = ViewModelProvider(this, userViewModelFactory).get(UserViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBazaarSettingsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // BACK BUTTON
        setUpBackButton()

        // FILL THE FIELDS
        fillInformation()

        // PUBLISH BUTTON
        setUpPublishButton()
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

    private fun setUpBackButton() {
        binding.settingsBackButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun fillInformation() {
        binding.userNameDisplayTV.text = user.value?.username
        binding.userNameTE.setText(user.value?.username)
        binding.emailTE.setText(user.value?.email)
        binding.phoneNumberTE.setText(user.value?.phone_number)
    }

    private fun setUpPublishButton() {
        binding.publishButtonSettings.setOnClickListener {
            lifecycleScope.launchWhenCreated {
                userViewModel.updateUser(
                    User(
                        username = view?.findViewById<AppCompatEditText>(R.id.userNameTE)?.text.toString(),
                        password = "",
                        email = view?.findViewById<AppCompatEditText>(R.id.emailTE)?.text.toString(),
                        phone_number = view?.findViewById<AppCompatEditText>(R.id.phoneNumberTE)?.text.toString()
                    )
                )
            }
            userViewModel.code.observe(viewLifecycleOwner) {
                when (userViewModel.code.value) {
                    200 -> {
                        Toast.makeText(requireContext(), "Update success.", Toast.LENGTH_LONG).show()
                    }
                    else -> {
                        Toast.makeText(requireContext(), "Unexpected error.", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}