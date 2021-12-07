package com.example.androidprojectdn2021.modelclasses

import com.squareup.moshi.JsonClass
import java.io.File

// USER
data class User(var username: String="", var password: String="", var email: String="", var phone_number: String="")

// LOGIN
@JsonClass(generateAdapter = true)
data class LoginRequest (
    var username: String,
    var password: String
)

@JsonClass(generateAdapter = true)
data class LoginResponse (
    var username: String,
    var email: String,
    var phone_number: Int,
    var token: String,
    var creation_time: Long,
    var refresh_time: Long
)

// REGISTER
@JsonClass(generateAdapter = true)
data class RegisterRequest(
    var username: String,
    var password: String,
    var email: String,
    var phone_number: String,
    //var firebase_token: String, // optional so I removed it
    //var userImage: File
)

@JsonClass(generateAdapter = true)
data class RegisterResponse (
    var code: String,
    var message: String,
    var creation_time: Long
)

// RESET PASSWORD - UNUSED
@JsonClass(generateAdapter = true)
data class ResetPasswordRequest (
    var username: String,
    var email: String
)

@JsonClass(generateAdapter = true)
data class ResetPasswordResponse (
    var code: Int,
    var message: String,
    var timestamp: Long
)





