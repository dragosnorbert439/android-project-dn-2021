package com.example.androidprojectdn2021.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.androidprojectdn2021.modelclasses.ResetPasswordRequest
import com.example.androidprojectdn2021.repository.Repository

class ForgotPasswordViewModel(val context: Context, val repository: Repository) : ViewModel() {
    lateinit var userName: String
    lateinit var email: String

    suspend fun resetPassword() {
        val request = ResetPasswordRequest(userName, email)
        try {
            val result = repository.resetPassword(request)
            Log.d("dnj", "ForgotPasswordViewModel - reset success")
            Toast.makeText(context, "Reset success!", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Log.d("dnj", "ForgotPasswordViewModel - $e")
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}