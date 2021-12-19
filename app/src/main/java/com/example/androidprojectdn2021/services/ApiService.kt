package com.example.androidprojectdn2021.services

import com.example.androidprojectdn2021.modelclasses.*
import com.example.androidprojectdn2021.utils.Constants
import retrofit2.http.*

interface ApiService {

    @POST(Constants.LOGIN_URL)
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST(Constants.REGISTER_URL)
    suspend fun register(@Body request: RegisterRequest): RegisterResponse

    @POST(Constants.RESET_PASSWORD_URL)
    suspend fun resetPassword(@Body request: ResetPasswordRequest): ResetPasswordResponse

    @GET(Constants.GET_PRODUCT_URL)
    suspend fun getProducts(@Header("token") token: String): ProductResponse

    @GET(Constants.GET_PRODUCT_URL)
    suspend fun getProductsFiltered(@HeaderMap query: Map<String, String>): ProductResponse
} 