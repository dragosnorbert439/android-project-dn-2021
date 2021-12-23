package com.example.androidprojectdn2021.login.register

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidprojectdn2021.modelclasses.RegisterRequest
import com.example.androidprojectdn2021.repository.Repository
import com.example.androidprojectdn2021.user.UserData.user
import retrofit2.HttpException

class RegisterViewModel(val context: Context, val repository: Repository): ViewModel() {
    var code: MutableLiveData<Int> = MutableLiveData()

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
        } catch (e: HttpException) {
            Log.d("dnj", "RegisterViewModel - exception: ${e.message}")
            code.value = e.code()
        }
    }
}