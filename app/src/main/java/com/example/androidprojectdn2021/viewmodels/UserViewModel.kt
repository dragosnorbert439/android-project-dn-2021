package com.example.androidprojectdn2021.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidprojectdn2021.modelclasses.UpdateUserRequest
import com.example.androidprojectdn2021.modelclasses.User
import com.example.androidprojectdn2021.repository.Repository
import com.example.androidprojectdn2021.user.Token.token
import java.lang.Exception

class UserViewModel(val repository: Repository) : ViewModel() {
    var user: MutableLiveData<User> = MutableLiveData()
    var initialized: Boolean = false

    init {
        user.value = User()
    }

    suspend fun updateUser(user: User) {
        val request = UpdateUserRequest(
            username = user.username,
            email = user.email,
            phone_number = user.phone_number
        )
        try {
            val result = repository.updateUser(token.value.toString(), request)
            Log.d("dnj", "UserViewModel - result success: $result")
            this.user.value = result.updatedData
        } catch (e: Exception) {
            Log.d("dnj", "UserViewModel - exception: $e")
        }
    }
}