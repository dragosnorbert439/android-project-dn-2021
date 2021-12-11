package com.example.androidprojectdn2021

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.androidprojectdn2021.databinding.ActivityMarketBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MarketActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMarketBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMarketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navViewMarket
        val navController = findNavController(R.id.fragmentContainerViewMarket)
        navView.setupWithNavController(navController)
    }
}