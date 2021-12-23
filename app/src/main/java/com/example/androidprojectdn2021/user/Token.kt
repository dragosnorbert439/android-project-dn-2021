package com.example.androidprojectdn2021.user

import androidx.lifecycle.MutableLiveData

object Token {
    var token: MutableLiveData<String> = MutableLiveData()
    var EXPIRED_TOKEN_FLAG: MutableLiveData<Short> = MutableLiveData()

    fun MutableLiveData<Short>.tick() {
        if (EXPIRED_TOKEN_FLAG.value != null) {
            if (EXPIRED_TOKEN_FLAG.value!! > 0) EXPIRED_TOKEN_FLAG.postValue(0)
            else EXPIRED_TOKEN_FLAG.postValue(1)
        }
        else EXPIRED_TOKEN_FLAG.postValue(0)
    }
}