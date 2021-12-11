package com.example.androidprojectdn2021.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

class BazaarTimelineFragment : Fragment(), DataAdapter.OnItemClickListener,
    DataAdapter.OnItemLongClickListener {
    lateinit var marketViewModel: MarketViewModel
    private lateinit var recycler_view: RecyclerView
    private lateinit var adapter: DataAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = MarketViewModelFactory(Repository())
        marketViewModel = ViewModelProvider(this, factory).get(MarketViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bazaar_timeline, container, false)

        recycler_view = view.findViewById(R.id.recyclerViewTimeline)
        setupRecyclerView()

        marketViewModel.products.observe(viewLifecycleOwner) {
            adapter.setData(marketViewModel.products.value as ArrayList<Product>)
            adapter.notifyDataSetChanged()
        }

        return view
    }

    private fun setupRecyclerView(){
        adapter = DataAdapter(ArrayList<Product>(), this.requireContext(), this, this)
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this.context)
        recycler_view.addItemDecoration(
            DividerItemDecoration(
                activity,
                DividerItemDecoration.VERTICAL
            )
        )
        recycler_view.setHasFixedSize(true)
    }

    companion object {
        @JvmStatic
        fun newInstance() = BazaarTimelineFragment()
    }

    override fun onItemClick(position: Int) {
        TODO("Not yet implemented")
    }

    override fun onItemLongClick(position: Int) {
        TODO("Not yet implemented")
    }
}