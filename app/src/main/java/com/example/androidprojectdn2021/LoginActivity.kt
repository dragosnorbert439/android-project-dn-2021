package com.example.androidprojectdn2021

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.androidprojectdn2021.modelclasses.UserLoggedIn
import com.example.androidprojectdn2021.services.ApiService
import com.example.androidprojectdn2021.utils.Constants.Companion.BASE_URL
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(ApiService::class.java)

        api.logIn("dragosnorbert_test", "testpassword")
            .enqueue(object: Callback<UserLoggedIn>{
                override fun onResponse(
                    call: Call<UserLoggedIn>,
                    response: Response<UserLoggedIn>
                ) {
                    Log.d("asd", response.body().toString())
                }

                override fun onFailure(call: Call<UserLoggedIn>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
    }
}