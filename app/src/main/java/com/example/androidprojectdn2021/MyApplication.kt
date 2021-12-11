package com.example.androidprojectdn2021

import android.app.Application

class MyApplication: Application(){
    companion object {
        var token: String = ""
        var refresh_time: Long = 0
    }
}