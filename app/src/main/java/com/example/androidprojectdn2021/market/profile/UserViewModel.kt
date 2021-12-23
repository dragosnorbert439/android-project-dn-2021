package com.example.androidprojectdn2021.market.profile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidprojectdn2021.modelclasses.UpdateUserRequest
import com.example.androidprojectdn2021.modelclasses.User
import com.example.androidprojectdn2021.repository.Repository
import com.example.androidprojectdn2021.user.Token.token
import com.example.androidprojectdn2021.user.UserData.user
import retrofit2.HttpException

class UserViewModel(val repository: Repository) : ViewModel() {
    var code: MutableLiveData<Int> = MutableLiveData()

    suspend fun updateUser(newUser: User) {
        val request = UpdateUserRequest(
            username = newUser.username,
            email = newUser.email,
            phone_number = newUser.phone_number
        )
        try {
            val result = repository.updateUser(token.value.toString(), request)
            Log.d("dnj", "UserViewModel - result success: $result")
            user.value = result.updatedData
            code.value = result.code
        } catch (e: HttpException) {
            Log.d("dnj", "UserViewModel - exception: $e")
            code.value = e.code()
        }
    }
}