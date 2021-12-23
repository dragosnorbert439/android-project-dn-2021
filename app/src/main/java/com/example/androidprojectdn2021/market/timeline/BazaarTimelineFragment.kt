package com.example.androidprojectdn2021.market.timeline

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidprojectdn2021.R
import com.example.androidprojectdn2021.adapters.DataAdapter
import com.example.androidprojectdn2021.databinding.FragmentBazaarTimelineBinding
import com.example.androidprojectdn2021.market.viewproduct.ViewProductViewModel
import com.example.androidprojectdn2021.market.viewproduct.ViewProductViewModelFactory
import com.example.androidprojectdn2021.modelclasses.Product
import com.example.androidprojectdn2021.repository.Repository
import com.google.android.material.textfield.TextInputEditText

class BazaarTimelineFragment : Fragment(), DataAdapter.OnItemClickListener,
    DataAdapter.OnItemLongClickListener, DataAdapter.OnOrderButtonClickListener,
    AdapterView.OnItemSelectedListener {
    lateinit var marketViewModel: MarketViewModel
    private val viewProductViewModel: ViewProductViewModel by activityViewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DataAdapter
    private lateinit var filterButton: Button
    private lateinit var filterView: View
    private lateinit var searchButton: Button
    private lateinit var searchView: View
    private lateinit var searchInputText: TextInputEditText
    private lateinit var settingsIV: ImageView
    private lateinit var spinner: Spinner
    private var sortMap = hashMapOf<String, String>()
    private var sortOrder: Int = 1
    private lateinit var switch: Switch
    private lateinit var binding: FragmentBazaarTimelineBinding
    private lateinit var builder: AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val marketViewModelFactory = MarketViewModelFactory(Repository())
        val viewProductModelFactory = ViewProductViewModelFactory()
        marketViewModel =
            ViewModelProvider(this, marketViewModelFactory).get(MarketViewModel::class.java)
        builder = AlertDialog.Builder(this.activity)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBazaarTimelineBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenCreated {
            marketViewModel.getProductsFiltered(hashMapOf())
        }

        // RECYCLER VIEW
        recyclerView = binding.recyclerViewTimeline
        setupRecyclerView()

        // OBSERVER ON PRODUCTS
        marketViewModel.products.observe(viewLifecycleOwner) {
            adapter.setData(marketViewModel.products.value as ArrayList<Product>)
            adapter.notifyDataSetChanged()
        }

        // SEARCH
        searchButton = binding.searchButtonMyMarketFragment
        searchView = binding.searchView
        searchView.visibility = View.GONE
        setUpSearchButton()

        // FILTER
        switch = binding.switchTimelineFragment
        setUpSwitch()
        filterView = binding.filterView
        filterButton = binding.filterButtonTimelineFragment
        searchInputText = binding.searchTextInput
        setUpFilterButton()

        // SETTINGS
        settingsIV = binding.userIVTimelineFragment
        setUpSettingsIV(view)

        // DROPDOWN
        spinner = binding.dropDownSpinnerTimelineFragment
        setUpDropDownSpinner()
    }

    private fun setupRecyclerView() {
        adapter = DataAdapter(ArrayList<Product>(), this.requireContext(),
            this, this, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.addItemDecoration(DividerItemDecoration(activity, 0))
        recyclerView.setHasFixedSize(true)
    }

    private fun setUpFilterButton() {
        filterButton.setOnClickListener {
            if (filterView.isVisible) {
                filterView.visibility = View.GONE
                when (spinner.selectedItem.toString()) {
                    "Price" -> {
                        sortMap["sort"] = "{\"price_per_unit\":${sortOrder}}"
                    }
                    "Seller" -> {
                        sortMap["sort"] = "{\"username\":${sortOrder}}"
                    }
                    "Time" -> {
                        sortMap["sort"] = "{\"creation_time\":${sortOrder}}"
                    }
                }
                marketViewModel.getProductsFiltered(sortMap)
            } else {
                filterView.visibility = View.VISIBLE
            }
        }
    }

    private fun setUpSwitch() {
        switch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                sortOrder = -1
                buttonView.text = "Descending"
            } else {
                sortOrder = 1
                buttonView.text = "Ascending"
            }
        }
    }

    private fun setUpSearchButton() {
        searchButton.setOnClickListener {
            if (searchView.isVisible) {
                searchView.visibility = View.GONE
                if (searchInputText.text?.isEmpty() == false) {
                    sortMap["filter"] = "{\"title\":\"${searchInputText.text.toString()}\"}"
                } else {
                    sortMap.remove("filter")
                }
                marketViewModel.getProductsFiltered(sortMap)
            } else {
                searchView.visibility = View.VISIBLE
            }
        }
    }

    private fun setUpSettingsIV(view: View) {
        settingsIV.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_bazaarTimelineFragment_to_bazaarSettingsFragment)
        }
    }

    private fun setUpDropDownSpinner() {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.filter_options_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
        spinner.onItemSelectedListener = this
    }

    override fun onItemClick(position: Int) {
        viewProductViewModel.product = marketViewModel.products.value?.get(position)!!
        view?.let {
            Navigation.findNavController(it)
                .navigate(R.id.action_bazaarTimelineFragment_to_viewProductFragment)
        }
    }

    override fun onItemLongClick(position: Int) {
        Toast.makeText(activity?.applicationContext, "onLongClick", Toast.LENGTH_SHORT).show()
    }

    override fun onButtonClick(position: Int) {
        Toast.makeText(activity?.applicationContext, "Order button clicked", Toast.LENGTH_SHORT)
            .show()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {}

    override fun onNothingSelected(parent: AdapterView<*>?) {}
}