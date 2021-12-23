package com.example.androidprojectdn2021.services

import com.example.androidprojectdn2021.modelclasses.*
import com.example.androidprojectdn2021.user.Token
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

    @POST(Constants.UPDATE_USER_URL)
    suspend fun updateUser(
        @Header("token") token: String,
        @Body request: UpdateUserRequest
    ): UpdateUserResponse

// doesn't work - probably no adapter on the back-end side
//    @POST(Constants.ADD_PRODUCT_URL)
//    suspend fun addProduct(
//        @Header("token") token: String,
//        @Body request: AddProductRequest
//    ): AddProductResponse

    @Multipart
    @POST(Constants.ADD_PRODUCT_URL)
    suspend fun addProduct(
        @Header("token") token: String = Token.token.value.toString(),
        @Part( "title") title: String,
        @Part("description") description: String,
        @Part("price_per_unit") price_per_unit: String,
        @Part("units") units: String,
        @Part("is_active") is_active: Boolean,
        @Part("rating") rating: Double,
        @Part("amount_type") amount_type: String,
        @Part("price_type") price_type: String
    ): AddProductResponse

    @POST(Constants.REMOVE_PRODUCT)
    suspend fun removeProduct(
        @Header("token") token: String,
        @Query("product_id") product_id: String
    ): RemoveProductResponse

    @GET(Constants.REFRESH_TOKEN)
    suspend fun refreshToken(
        @Header("token") token: String = Token.token.value.toString()
    ): RefreshTokenResponse
}