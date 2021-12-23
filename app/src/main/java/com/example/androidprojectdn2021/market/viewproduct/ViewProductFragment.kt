package com.example.androidprojectdn2021.market.viewproduct

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.androidprojectdn2021.R
import com.example.androidprojectdn2021.utils.convertLongToTime


class ViewProductFragment : Fragment() {
    private val viewProductViewModel: ViewProductViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_view_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewProductViewModel.product?.let {
            view.findViewById<TextView>(R.id.titleTextViewVPL).text =
                viewProductViewModel.product.title
            view.findViewById<TextView>(R.id.usernameTextViewVPL).text =
                viewProductViewModel.product.username
            view.findViewById<TextView>(R.id.dateTextViewVPL).text =
                convertLongToTime(viewProductViewModel.product.creation_time)
            view.findViewById<TextView>(R.id.descriptionTextViewVPL).text =
                viewProductViewModel.product.description
            view.findViewById<TextView>(R.id.priceTextViewVPL).text =
                viewProductViewModel.product.price_per_unit + " " + viewProductViewModel.product.price_type + "/" + viewProductViewModel.product.amount_type
            view.findViewById<TextView>(R.id.priceTVVPL).text =
                viewProductViewModel.product.price_per_unit + " " + viewProductViewModel.product.price_type + "/" + viewProductViewModel.product.amount_type
            if (viewProductViewModel.product.is_active) {
                view.findViewById<TextView>(R.id.statusTextViewVPL).text = "Active"
                view.findViewById<ImageView>(R.id.checkImageViewVPL).setBackgroundResource(R.drawable.ic_check)
            }
            else {
                view.findViewById<TextView>(R.id.statusTextViewVPL).text = "Inactive"
                view.findViewById<ImageView>(R.id.checkImageViewVPL).setBackgroundResource(R.drawable.ic_non_check)
            }
            view.findViewById<TextView>(R.id.ratingTVVPL).text = viewProductViewModel.product.rating.toString()
            view.findViewById<TextView>(R.id.amountViewVLP).text = viewProductViewModel.product.units
        }

        setUpBackButton()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback = object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    private fun setUpBackButton() {
        view?.findViewById<Button>(R.id.detailsBackButton)?.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}