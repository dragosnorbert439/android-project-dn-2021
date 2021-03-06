package com.example.androidprojectdn2021.login.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidprojectdn2021.modelclasses.LoginRequest
import com.example.androidprojectdn2021.modelclasses.User
import com.example.androidprojectdn2021.repository.Repository
import com.example.androidprojectdn2021.user.Token.token
import com.example.androidprojectdn2021.user.UserData.user
import retrofit2.HttpException

class LoginViewModel(val context: Context, val repository: Repository) : ViewModel() {
    var code: MutableLiveData<Int> = MutableLiveData()

    init {
        // here's initialized the user (only here)
        user.value = User()
    }

    suspend fun login() {
        val request =
            LoginRequest(username = user.value!!.username, password = user.value!!.password)
        try {
            val result = repository.login(request)
            user.value!!.username = result.username
            user.value!!.email = result.email
            user.value!!.phone_number = result.phone_number.toString()
            token.value = result.token // it is important to leave this one the last
            Log.d("dnj", "$result")
            Log.d("dnj", "LoginViewModel - token: ${token.value}")
        } catch (e: HttpException) {
            Log.d("dnj", "LoginViewModel - exception: ${e.toString()}")
            code.value = e.code()
        }
    }
}