package com.example.androidprojectdn2021.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidprojectdn2021.R
import com.example.androidprojectdn2021.adapters.DataAdapter
import com.example.androidprojectdn2021.adapters.DataAdapterMyProducts
import com.example.androidprojectdn2021.modelclasses.Product
import com.example.androidprojectdn2021.repository.Repository
import com.example.androidprojectdn2021.user.UserData.user
import com.example.androidprojectdn2021.viewmodels.MarketViewModel
import com.example.androidprojectdn2021.viewmodels.MarketViewModelFactory

class BazaarMyMarketFragment : Fragment(), DataAdapterMyProducts.OnItemClickListener {
    lateinit var marketViewModel: MarketViewModel
    private lateinit var recycler_view: RecyclerView
    private lateinit var adapter: DataAdapterMyProducts

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
        val view = inflater.inflate(R.layout.fragment_bazaar_my_market, container, false)

        lifecycleScope.launchWhenCreated {
            marketViewModel.getProductsFiltered(hashMapOf("filter" to "{\"username\":\"${user.value?.username}\"}"))
        }

        // RECYCLER VIEW
        recycler_view = view.findViewById(R.id.recyclerViewMyMarket)
        setupRecyclerView()

        // OBSERVER ON PRODUCTS
        marketViewModel.products.observe(viewLifecycleOwner) {
            adapter.setData(marketViewModel.products.value as ArrayList<Product>)
            adapter.notifyDataSetChanged()
        }

        // ADD PRODUCT BUTTON
        val addProductButton = view.findViewById<AppCompatButton>(R.id.addProductButtonMyMarketFragment)
        addProductButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_bazaarMyMarketFragment_to_addProductFragment)
        }

        return view
    }

    private fun setupRecyclerView() {
        adapter = DataAdapterMyProducts(ArrayList<Product>(), this.requireContext(), this)
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this.context)
        recycler_view.addItemDecoration(
            DividerItemDecoration(
                activity, 0
            )
        )
        recycler_view.setHasFixedSize(true)
    }

    override fun onItemClick(position: Int) {
        Toast.makeText(requireContext(), "Item clicked.", Toast.LENGTH_SHORT).show()
    }

}