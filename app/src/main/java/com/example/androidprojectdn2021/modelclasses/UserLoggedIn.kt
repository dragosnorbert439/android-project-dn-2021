package com.example.androidprojectdn2021.modelclasses

data class UserLoggedIn (   val username: String,
                            val email: String,
                            val phone_number: Int,
                            val token: String,
                            val creation_time: Int,
                            val refresh_time: Int
                            ) {}