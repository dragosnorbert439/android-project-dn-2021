package com.example.androidprojectdn2021.market.viewproduct

import androidx.lifecycle.ViewModel
import com.example.androidprojectdn2021.modelclasses.Product

class ViewProductViewModel(): ViewModel() {
    var product: Product = Product(0.0, "", "", "", "", false, "", "", "", "", listOf(), 0)
}