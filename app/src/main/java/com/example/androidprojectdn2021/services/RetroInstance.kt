package com.example.androidprojectdn2021.services

import com.example.androidprojectdn2021.converter.StringConverterFactory
import com.example.androidprojectdn2021.utils.Constants.BASE_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import retrofit2.converter.gson.GsonConverterFactory
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat

import androidx.core.content.ContextCompat.startActivity
import okhttp3.*
import com.example.androidprojectdn2021.repository.*
import com.example.androidprojectdn2021.user.Token
import com.example.androidprojectdn2021.user.Token.tick
import kotlinx.coroutines.coroutineScope
import kotlin.experimental.and


object RetrofitInstance {
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofitString by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(StringConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // for debugging
    private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS)

    private val myClientDefault = OkHttpClient
        .Builder()
        .apply {
            readTimeout(1, TimeUnit.MINUTES)
            writeTimeout(1, TimeUnit.MINUTES)
            connectTimeout(1, TimeUnit.MINUTES)
        }

    private val myClient = OkHttpClient
        .Builder()
        .addInterceptor(Interceptor { chain ->
            val request: Request = chain.request()
            val response = chain.proceed(request)

            if (response.code in 301..302) {

                Log.d("dnj", "RetrofitInstance - response code: ${response.code}")
                Token.EXPIRED_TOKEN_FLAG.tick()
            }

            return@Interceptor response
        })

    val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .client(myClient.build())
        .build()

    val retrofitDefault = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .client(myClientDefault.build())
        .build()

    val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    // this one converts strings correctly with the custom converter -> no quotation marks
    val api2: ApiService by lazy {
        retrofitString.create(ApiService::class.java)
    }

    // this one has a default client which doesn't have an interceptor
    // if it did, it would just call it over and over recursively
    // because I have a http call in the interceptor
    val api3: ApiService by lazy {
        retrofitDefault.create(ApiService::class.java)
    }
}

