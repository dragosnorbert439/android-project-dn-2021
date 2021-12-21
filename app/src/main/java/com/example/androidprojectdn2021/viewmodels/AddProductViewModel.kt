package com.example.androidprojectdn2021.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidprojectdn2021.modelclasses.AddProductRequest
import com.example.androidprojectdn2021.modelclasses.AddProductResponse
import com.example.androidprojectdn2021.modelclasses.Product
import com.example.androidprojectdn2021.repository.Repository
import kotlinx.coroutines.launch

class AddProductViewModel(val context: Context, val repository: Repository) : ViewModel() {
    var addProductResponse: MutableLiveData<AddProductResponse> = MutableLiveData()
    var addProductRequest: AddProductRequest = AddProductRequest("", "", "", 0, false, 0.0, "", "")

    suspend fun addProduct() {
        viewModelScope.launch {

        }
    }
}