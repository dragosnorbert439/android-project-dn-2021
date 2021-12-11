package com.example.androidprojectdn2021.fragments

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.androidprojectdn2021.R

class BazaarSplashFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val handler = Handler()
        handler.postDelayed({
            view?.let { Navigation.findNavController(it).navigate(R.id.action_bazaarSplashFragment3_to_loadingFragment) }
        },500)
        return inflater.inflate(R.layout.fragment_bazaar_splash, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = BazaarSplashFragment()
    }
}