package com.example.androidprojectdn2021.market.addproduct

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidprojectdn2021.modelclasses.AddProductRequest
import com.example.androidprojectdn2021.repository.Repository
import com.example.androidprojectdn2021.user.Token.token
import retrofit2.HttpException

class AddProductViewModel(val context: Context, val repository: Repository) : ViewModel() {
    var code: MutableLiveData<Int> = MutableLiveData()
    var addProductRequest: AddProductRequest = AddProductRequest("", "", "", "", false, 0.0, "", "")

    init {
        code.value = 0
    }

    suspend fun addProduct() {
        try {
            val result = repository.addProduct(
                token = token.value.toString(),
                title = addProductRequest.title,
                description = addProductRequest.description,
                price_per_unit = addProductRequest.price_per_unit,
                units = addProductRequest.units,
                is_active = addProductRequest.is_active,
                rating = addProductRequest.rating,
                amount_type = addProductRequest.amount_type,
                price_type = addProductRequest.price_type
            )
            Log.d("dnj", "AddProductViewModel - result: $result")
        } catch (e: HttpException) {
            code.value = e.code()
            Log.d("dnj", "AddProductViewModel - exception: ${e.toString()}")
        }
    }
}