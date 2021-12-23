package com.example.androidprojectdn2021.market.viewproduct

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewProductViewModelFactory(): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ViewProductViewModel() as T
    }
}