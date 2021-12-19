package com.example.androidprojectdn2021.viewmodels

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
        getProductsFiltered(hashMapOf())
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

    fun getProductsFiltered(map: HashMap<String, String>) {
        viewModelScope.launch {
            try {
                map["token"] = MyApplication.token
                map["limit"] = "50"
                Log.d("dnj", map.toString())
                val result = repository.getProductsFiltered(map)
                products.value = result.products
                Log.d("dnj", "MarketViewModel - #products: ${result.item_count}")
            } catch(e: Exception) {
                Log.d("dnj", "MarketViewModel exception: ${e.toString()}")
            }
        }
    }
}