package com.example.androidprojectdn2021.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.androidprojectdn2021.R
import com.example.androidprojectdn2021.modelclasses.AddProductRequest
import com.example.androidprojectdn2021.repository.Repository
import com.example.androidprojectdn2021.user.UserData.user
import com.example.androidprojectdn2021.viewmodels.AddProductViewModel
import com.example.androidprojectdn2021.viewmodels.AddProductViewModelFactory
import com.google.android.material.textfield.TextInputEditText


class BazaarAddProductFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private lateinit var currencyDDSpinner: Spinner
    private lateinit var amountDDSpinner: Spinner
    private lateinit var backButton: AppCompatButton
    private lateinit var previewButton: AppCompatButton
    private lateinit var launchButton: AppCompatButton
    private lateinit var addProductViewModel: AddProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val addProductViewModelFactory = AddProductViewModelFactory(requireContext(), Repository())
        addProductViewModel = ViewModelProvider(this, addProductViewModelFactory).get(AddProductViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_product, container, false)

        // CONTACT DETAILS
        view.findViewById<TextInputEditText>(R.id.userNameTITEAddProdLayout)
            .setText(user.value?.username)
        view.findViewById<TextInputEditText>(R.id.emailTITEAddProdLayout).setText(user.value?.email)
        view.findViewById<TextInputEditText>(R.id.phoneNumberTITEAddProdLayout)
            .setText(user.value?.phone_number)

        // BACK BUTTON
        backButton = view.findViewById<AppCompatButton>(R.id.createFareBackACButtonAPL)
        setUpBackButton()

        // PREVIEW BUTTON
        previewButton = view.findViewById<AppCompatButton>(R.id.previewMyFairACButtonAddProdLayout)
        setUpPreviewButton()

        // LAUNCH BUTTON
        launchButton = view.findViewById<AppCompatButton>(R.id.launchMyFairACButtonAddProdLayout)
        launchButton.setOnClickListener {
            val title = view.findViewById<TextInputEditText>(R.id.titleTITEAddProductLayout)
            val price = view.findViewById<TextInputEditText>(R.id.priceTITEAddProductLayout)
            val amount = view.findViewById<TextInputEditText>(R.id.amountTITEAddProdLayout)
            val description = view.findViewById<TextInputEditText>(R.id.descriptionTITEAddProdLayout)
            // these 3 are on the Figma design but are not required for the request
            val username = view.findViewById<TextInputEditText>(R.id.userNameTITEAddProdLayout)
            val email = view.findViewById<TextInputEditText>(R.id.emailTITEAddProdLayout)
            val phone = view.findViewById<TextInputEditText>(R.id.phoneNumberTITEAddProdLayout)
            val isActive: Boolean = view.findViewById<Switch>(R.id.activeSwitchAddProductLayout).isChecked
            val amountType = view.findViewById<Spinner>(R.id.dropDownSpinnerAmountAPL).selectedItem
            val priceType = view.findViewById<Spinner>(R.id.dropDownSpinnerCurrencyAPL).selectedItem
            if (title.text?.isEmpty() == true) {
                Toast.makeText(requireContext(), "Title empty!", Toast.LENGTH_SHORT).show()
            } else if (price.text?.isEmpty() == true) {
                Toast.makeText(requireContext(), "Price empty!", Toast.LENGTH_SHORT).show()
            } else if (amount.text?.isEmpty() == true) {
                Toast.makeText(requireContext(), "Amount empty!", Toast.LENGTH_SHORT).show()
            } else if (description.text?.isEmpty() == true) {
                Toast.makeText(requireContext(), "Description empty!", Toast.LENGTH_SHORT).show()
            } else {
                addProductViewModel.addProductRequest = AddProductRequest(
                    title = title.text.toString(),
                    description = description.text.toString(),
                    price_per_unit = price.text.toString(),
                    unit = amount.text.toString().toInt(),
                    is_active = isActive,
                    rating = 0.0,
                    amount_type = amountType.toString(),
                    price_type = priceType.toString()
                )

                Log.d("dnj", "${addProductViewModel.addProductRequest}")
            }
        }

        // DROP DOWN SPINNERS
        currencyDDSpinner = view.findViewById<Spinner>(R.id.dropDownSpinnerCurrencyAPL)
        amountDDSpinner = view.findViewById<Spinner>(R.id.dropDownSpinnerAmountAPL)
        setUpDropDownSpinners()



        return view
    }

    private fun setUpBackButton() {
        backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setUpPreviewButton() {
        previewButton.setOnClickListener {
            Toast.makeText(requireContext(), "Preview button clicked", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setUpLaunchButton(view: View) {
        launchButton.setOnClickListener {

        }
    }

    private fun setUpDropDownSpinners() {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.currency_options_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            currencyDDSpinner.adapter = adapter
        }
        currencyDDSpinner.onItemSelectedListener = this

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.amount_options_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            amountDDSpinner.adapter = adapter
        }
        amountDDSpinner.onItemSelectedListener = this
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {}

    override fun onNothingSelected(parent: AdapterView<*>?) {}
}