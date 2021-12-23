package com.example.androidprojectdn2021.market.timeline

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidprojectdn2021.repository.Repository

class MarketViewModelFactory (private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MarketViewModel(repository) as T
    }
}