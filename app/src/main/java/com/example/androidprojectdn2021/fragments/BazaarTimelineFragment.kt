package com.example.androidprojectdn2021.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidprojectdn2021.R
import com.example.androidprojectdn2021.adapters.DataAdapter
import com.example.androidprojectdn2021.modelclasses.Product
import com.example.androidprojectdn2021.repository.Repository
import com.example.androidprojectdn2021.viewmodels.MarketViewModel
import com.example.androidprojectdn2021.viewmodels.MarketViewModelFactory
import com.example.androidprojectdn2021.viewmodels.UserViewModel
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.coroutineScope

class BazaarTimelineFragment : Fragment(), DataAdapter.OnItemClickListener,
    DataAdapter.OnItemLongClickListener, DataAdapter.OnOrderButtonClickListener,
    AdapterView.OnItemSelectedListener {
    lateinit var marketViewModel: MarketViewModel
    lateinit var userViewModel: UserViewModel
    private lateinit var recycler_view: RecyclerView
    private lateinit var adapter: DataAdapter
    private lateinit var filterButton: Button
    private lateinit var filterView: View
    private lateinit var searchButton: Button
    private lateinit var searchView: View
    private lateinit var searchInputText: TextInputEditText
    private lateinit var settingsIV: ImageView
    private lateinit var spinner: Spinner
    private var sortMap = hashMapOf<String, String>()
    private var latestOldest: Short = 1
    private lateinit var switch: Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val marketViewModelFactory = MarketViewModelFactory(Repository())
        marketViewModel =
            ViewModelProvider(this, marketViewModelFactory).get(MarketViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bazaar_timeline, container, false)

        lifecycleScope.launchWhenCreated {
            marketViewModel.getProductsFiltered(hashMapOf())
        }

        // RECYCLER VIEW
        recycler_view = view.findViewById(R.id.recyclerViewTimeline)
        setupRecyclerView()

        // OBSERVER ON PRODUCTS
        marketViewModel.products.observe(viewLifecycleOwner) {
            adapter.setData(marketViewModel.products.value as ArrayList<Product>)
            adapter.notifyDataSetChanged()
        }

        // SEARCH
        searchButton = view.findViewById(R.id.searchButtonMyMarketFragment)
        searchView = view.findViewById(R.id.searchView)
        searchView.visibility = View.GONE
        setUpSearchButton()

        // FILTER
        switch = view.findViewById(R.id.switchTimelineFragment)
        setUpSwitch()
        filterView = view.findViewById(R.id.filterView)
        filterButton = view.findViewById(R.id.filterButtonTimelineFragment)
        searchInputText = view.findViewById(R.id.searchTextInput)
        setUpFilterButton()

        // SETTINGS
        settingsIV = view.findViewById(R.id.userIVTimelineFragment)
        setUpSettingsIV(view)

        // DROPDOWN
        spinner = view.findViewById(R.id.dropDownSpinnerTimelineFragment)
        setUpDropDownSpinner()

        return view
    }

    private fun setupRecyclerView() {
        adapter = DataAdapter(ArrayList<Product>(), this.requireContext(), this, this, this)
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this.context)
        recycler_view.addItemDecoration(
            DividerItemDecoration(
                activity, 0
            )
        )
        recycler_view.setHasFixedSize(true)
    }

    private fun setUpFilterButton() {
        filterButton.setOnClickListener {
            if (filterView.isVisible) {
                filterView.visibility = View.GONE
            } else {
                sortMap.remove("sort")
                filterView.visibility = View.VISIBLE
            }
            marketViewModel.getProductsFiltered(sortMap)
        }
    }

    private fun setUpSwitch() {
        switch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                latestOldest = -1
                buttonView.text = "Descending"
                Toast.makeText(requireContext(), "1", Toast.LENGTH_SHORT).show()
            } else {
                latestOldest = 1
                buttonView.text = "Ascending"
                Toast.makeText(requireContext(), "-1", Toast.LENGTH_SHORT).show()
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
        Toast.makeText(activity?.applicationContext, "onClick", Toast.LENGTH_SHORT).show()
    }

    override fun onItemLongClick(position: Int) {
        Toast.makeText(activity?.applicationContext, "onLongClick", Toast.LENGTH_SHORT).show()
    }

    override fun onButtonClick(position: Int) {
        Toast.makeText(activity?.applicationContext, "Order button clicked", Toast.LENGTH_SHORT)
            .show()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (spinner.getItemAtPosition(position).toString()) {
            "Price" -> {
                sortMap["sort"] = "{\"price_per_unit\":$latestOldest}"
            }
            "Seller" -> {
                sortMap["sort"] = "{\"username\":$latestOldest}"
            }
            "Time" -> {
                sortMap["sort"] = "{\"creation_time\":$latestOldest}"
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}
}