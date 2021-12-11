package com.example.androidprojectdn2021.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidprojectdn2021.MyApplication
import com.example.androidprojectdn2021.modelclasses.Product
import com.example.androidprojectdn2021.repository.Repository
import kotlinx.coroutines.launch

class MarketViewModel (val repository: Repository) : ViewModel() {
    var products: MutableLiveData<List<Product>> = MutableLiveData()

    init {
        Log.d("dnj", "MarketViewModel constructor - Token: ${MyApplication.token}")
        getProducts()
    }

    fun getProducts() {
        viewModelScope.launch {
            try {
                val result = repository.getProducts(MyApplication.token)
                products.value = result.products
                Log.d("dnj", "MarketViewModel - #products: ${result.item_count}")
            } catch(e: Exception) {
                Log.d("dnj", "MarketViewModel exception: ${e.toString()}")
            }
        }
    }
}