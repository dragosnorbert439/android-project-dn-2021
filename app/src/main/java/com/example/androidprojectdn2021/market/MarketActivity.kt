package com.example.androidprojectdn2021.market

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.androidprojectdn2021.R
import com.example.androidprojectdn2021.databinding.ActivityMarketBinding
import com.example.androidprojectdn2021.repository.Repository
import com.example.androidprojectdn2021.user.Token
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.lang.Exception

class MarketActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMarketBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMarketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navViewMarket
        val navController = findNavController(R.id.fragmentContainerViewMarket)
        navView.setupWithNavController(navController)

        Token.EXPIRED_TOKEN_FLAG.observe(this, Observer { it ->
            lifecycleScope.launchWhenCreated {
                try {
                    Log.d("dnj", "FLAG - ${Token.EXPIRED_TOKEN_FLAG.value}")
                    Log.d("dnj", "MarketActivity - token: ${Token.token.value}")
                    val response = Repository().refreshToken(Token.token.value.toString())
                    Log.d("dnj", "Token refresh success.")
                    Token.token.value = response.token
                    Log.d("dnj", "Market Activity - new token: $response")
                } catch (e: Exception) {
                    Log.d("dnj", "Token refresh exception - $e")
                }
            }
        })
    }
}