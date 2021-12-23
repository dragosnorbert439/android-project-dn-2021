package com.example.androidprojectdn2021.repository

import com.example.androidprojectdn2021.modelclasses.*
import com.example.androidprojectdn2021.services.RetrofitInstance
import com.example.androidprojectdn2021.user.Token

class Repository {

    // LOGIN ACTIVITY
    suspend fun login(request: LoginRequest): LoginResponse {
        return RetrofitInstance.api3.login(request)
    }

    suspend fun register(request: RegisterRequest): RegisterResponse {
        return RetrofitInstance.api3.register(request)
    }

    suspend fun resetPassword(request: ResetPasswordRequest): ResetPasswordResponse {
        return RetrofitInstance.api3.resetPassword(request)
    }

    // MARKET ACTIVITY
    suspend fun getProducts(token: String): ProductResponse {
        return RetrofitInstance.api.getProducts(token)
    }

    suspend fun getProductsFiltered(query: Map<String, String>): ProductResponse {
        return RetrofitInstance.api.getProductsFiltered(query)
    }

    suspend fun updateUser(token: String, request: UpdateUserRequest): UpdateUserResponse {
        return RetrofitInstance.api.updateUser(token, request)
    }

    suspend fun addProduct(
        token: String = Token.token.value.toString(),
        title: String,
        description: String,
        price_per_unit: String,
        units: String,
        is_active: Boolean,
        rating: Double,
        amount_type: String,
        price_type: String
    ): AddProductResponse {
        return RetrofitInstance.api2.addProduct(
            token,
            title,
            description,
            price_per_unit,
            units,
            is_active,
            rating,
            amount_type,
            price_type
        )
    }

    suspend fun removeProduct(token: String, product_id: String): RemoveProductResponse {
        return RetrofitInstance.api.removeProduct(token, product_id)
    }

    suspend fun refreshToken(
        token: String = Token.token.value.toString()
    ): RefreshTokenResponse {
        return RetrofitInstance.api3.refreshToken(token)
    }
}