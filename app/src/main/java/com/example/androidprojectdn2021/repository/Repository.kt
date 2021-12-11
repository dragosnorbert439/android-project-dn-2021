package com.example.androidprojectdn2021.repository

import com.example.androidprojectdn2021.modelclasses.*
import com.example.androidprojectdn2021.services.RetrofitInstance

class Repository {

    // LOGIN ACTIVITY
    suspend fun login(request: LoginRequest): LoginResponse {
        return RetrofitInstance.api.login(request)
    }
    suspend fun register(request: RegisterRequest): RegisterResponse {
        return RetrofitInstance.api.register(request)
    }

    // MARKET ACTIVITY
    suspend fun getProducts(token: String): ProductResponse {
        return RetrofitInstance.api.getProducts(token)
    }
}