package com.example.androidprojectdn2021.login.forgotpassword

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidprojectdn2021.modelclasses.ResetPasswordRequest
import com.example.androidprojectdn2021.repository.Repository
import retrofit2.HttpException

class ForgotPasswordViewModel(val context: Context, val repository: Repository) : ViewModel() {
    lateinit var userName: String
    lateinit var email: String
    var code: MutableLiveData<Int> = MutableLiveData()

    suspend fun resetPassword() {
        val request = ResetPasswordRequest(userName, email)
        try {
            val result = repository.resetPassword(request)
            Log.d("dnj", "ForgotPasswordViewModel - reset success")
            Toast.makeText(context, "Reset success!", Toast.LENGTH_SHORT).show()
            code.value = result.code
        } catch (e: HttpException) {
            code.value = e.code()
            Log.d("dnj", "ForgotPasswordViewModel - code: ${code.value.toString()}")
        }
    }
}