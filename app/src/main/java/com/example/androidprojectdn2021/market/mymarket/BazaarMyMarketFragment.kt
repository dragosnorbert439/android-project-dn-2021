package com.example.androidprojectdn2021.market.mymarket

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidprojectdn2021.R
import com.example.androidprojectdn2021.adapters.DataAdapterMyProducts
import com.example.androidprojectdn2021.modelclasses.Product
import com.example.androidprojectdn2021.repository.Repository
import com.example.androidprojectdn2021.user.UserData.user
import com.example.androidprojectdn2021.market.timeline.MarketViewModel
import com.example.androidprojectdn2021.market.timeline.MarketViewModelFactory

class BazaarMyMarketFragment : Fragment(), DataAdapterMyProducts.OnItemClickListener,
    DataAdapterMyProducts.OnItemLongClickListener {
    lateinit var marketViewModel: MarketViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DataAdapterMyProducts
    private lateinit var builder: AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val marketViewModelFactory = MarketViewModelFactory(Repository())
        marketViewModel =
            ViewModelProvider(this, marketViewModelFactory).get(MarketViewModel::class.java)
        builder = AlertDialog.Builder(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bazaar_my_market, container, false)

        lifecycleScope.launchWhenCreated {
            marketViewModel.getProductsFiltered(hashMapOf("filter" to "{\"username\":\"${user.value?.username}\"}"))
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RECYCLER VIEW
        recyclerView = view.findViewById(R.id.recyclerViewMyMarket)
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

        // SETTINGS BUTTON
        val settingsButton = view.findViewById<ImageView>(R.id.userIVMyMarketFragment)
        settingsButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_bazaarMyMarketFragment_to_bazaarSettingsFragment)
        }
    }

    private fun setupRecyclerView() {
        adapter = DataAdapterMyProducts(ArrayList<Product>(), this.requireContext(), this, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                activity, 0
            )
        )
        recyclerView.setHasFixedSize(true)
    }

    override fun onItemClick(position: Int) {
        Toast.makeText(requireContext(), "Item clicked.", Toast.LENGTH_SHORT).show()
    }

    override fun onItemLongClick(position: Int) {
        builder.setTitle("What would you like to do?")
            .setMessage("Choose one")
            .setPositiveButton("Nothing") {_: DialogInterface, _: Int -> {} }
            .setNegativeButton("Remove product") { _: DialogInterface, _: Int ->
                run {
                    marketViewModel.removeProduct(marketViewModel.products.value?.get(position)?.product_id.toString())
                }
            }
        builder.show()
    }
}