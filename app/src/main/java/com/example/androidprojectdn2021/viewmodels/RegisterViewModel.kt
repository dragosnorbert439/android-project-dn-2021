package com.example.androidprojectdn2021.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidprojectdn2021.modelclasses.RegisterRequest
import com.example.androidprojectdn2021.modelclasses.RegisterResponse
import com.example.androidprojectdn2021.modelclasses.User
import com.example.androidprojectdn2021.repository.Repository

class RegisterViewModel(val context: Context, val repository: Repository) : ViewModel() {
    var user = MutableLiveData<User>()
    var code: MutableLiveData<Int> = MutableLiveData()

    init {
        user.value = User()
    }

    suspend fun register() {
        val request =
            RegisterRequest(
                username = user.value!!.username,
                password = user.value!!.password,
                email = user.value!!.email,
                phone_number = user.value!!.phone_number
            )

        try {
            val result = repository.register(request)
            code.value = result.code.toInt()
            Log.d("dnj", "${code.value}")
            Log.d("dnj", "RegisterViewModel - message: ${result.message}")
        } catch (e: Exception) {
            Log.d("dnj", "RegisterViewModel - exception: ${e.message}")
            try {
                code.value = e.message?.substring(5, 8)?.toInt()
                Log.d("dnj", "RegisterViewModel - code: ${code.value.toString()}")
            }
            catch (e2: Exception) {
                Log.d("dnj", "RegisterViewModel - exception: ${e2.message}")
            }
        }
    }
}