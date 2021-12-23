package com.example.androidprojectdn2021.login.loading

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.androidprojectdn2021.R

class LoadingFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_loading_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val handler = Handler()
        handler.postDelayed({
            view?.let {
                Navigation.findNavController(it)
                    .navigate(R.id.action_loadingFragment_to_bazaarLoginFragment)
            }
        },1000)
    }

    companion object {
        @JvmStatic
        fun newInstance() = LoadingFragment()
    }
}