package com.example.androidprojectdn2021.login.forgotpassword

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidprojectdn2021.repository.Repository

class ForgotPasswordViewModelFactory(private val context: Context, private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ForgotPasswordViewModel(context, repository) as T
    }
}