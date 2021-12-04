package com.example.androidprojectdn2021.services

import com.example.androidprojectdn2021.modelclasses.UserLoggedIn
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("user/login")
    fun logIn(username: String, password: String): Call<UserLoggedIn>
}