package com.example.androidprojectdn2021.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.androidprojectdn2021.R

class BazaarMyFaresFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bazaar_my_fares, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = BazaarMyFaresFragment()
    }
}