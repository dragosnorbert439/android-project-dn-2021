package com.example.androidprojectdn2021.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidprojectdn2021.R
import com.example.androidprojectdn2021.adapters.DataAdapter
import com.example.androidprojectdn2021.modelclasses.Product
import com.example.androidprojectdn2021.repository.Repository
import com.example.androidprojectdn2021.viewmodels.MarketViewModel
import com.example.androidprojectdn2021.viewmodels.MarketViewModelFactory
import com.google.android.material.appbar.AppBarLayout

class BazaarTimelineFragment : Fragment(), DataAdapter.OnItemClickListener,
    DataAdapter.OnItemLongClickListener, DataAdapter.OnOrderButtonClickListener {
    lateinit var marketViewModel: MarketViewModel
    private lateinit var recycler_view: RecyclerView
    private lateinit var adapter: DataAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        val factory = MarketViewModelFactory(Repository())
        marketViewModel = ViewModelProvider(this, factory).get(MarketViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bazaar_timeline, container, false)
        setHasOptionsMenu(true)

        // RECYCLER VIEW
        recycler_view = view.findViewById(R.id.recyclerViewTimeline)
        setupRecyclerView()

        // OBSERVER ON PRODUCTS
        marketViewModel.products.observe(viewLifecycleOwner) {
            adapter.setData(marketViewModel.products.value as ArrayList<Product>)
            adapter.notifyDataSetChanged()
        }

        // APP BAR
        // SEARCH
        view.findViewById<Button>(R.id.searchButtonActivityMarket).setOnClickListener {
            Toast.makeText(context, "Search button clicked", Toast.LENGTH_SHORT).show()
        }
        // FILTER
        view.findViewById<Button>(R.id.filterButtonActivityMarket).setOnClickListener {
            Toast.makeText(context, "Filter button clicked", Toast.LENGTH_SHORT).show()
        }
        // SETTINGS
        view.findViewById<ImageView>(R.id.userIVTimelineFragment).setOnClickListener {
            Toast.makeText(context, "Settings clicked", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    private fun setupRecyclerView(){
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

    companion object {
        @JvmStatic
        fun newInstance() = BazaarTimelineFragment()
    }

    override fun onItemClick(position: Int) {
        Toast.makeText(activity?.applicationContext, "onClick", Toast.LENGTH_SHORT).show()
    }

    override fun onItemLongClick(position: Int) {
        Toast.makeText(activity?.applicationContext, "onLongClick", Toast.LENGTH_SHORT).show()
    }

    override fun onItemOrderButtonClick(position: Int) {
        Toast.makeText(activity?.applicationContext, "Order button clicked", Toast.LENGTH_SHORT).show()
    }
}