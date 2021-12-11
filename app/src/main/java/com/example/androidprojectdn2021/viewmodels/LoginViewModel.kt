package com.example.androidprojectdn2021.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidprojectdn2021.MyApplication
import com.example.androidprojectdn2021.modelclasses.LoginRequest
import com.example.androidprojectdn2021.modelclasses.User
import com.example.androidprojectdn2021.repository.Repository

class LoginViewModel(val context: Context, val repository: Repository) : ViewModel() {
    var token: MutableLiveData<String> = MutableLiveData()
    var user = MutableLiveData<User>()

    init {
        user.value = User()
    }

    suspend fun login() {
        val request =
            LoginRequest(username = user.value!!.username, password = user.value!!.password)
        try {
            val result = repository.login(request)
            MyApplication.token = result.token
            MyApplication.refresh_time = result.refresh_time
            token.value = result.token
            Log.d("dnj", "MyApplication - token: ${MyApplication.token}")
        } catch (e: Exception) {
            Log.d("dnj", "LoginViewModel - exception: ${e.toString()}")
        }
    }
}