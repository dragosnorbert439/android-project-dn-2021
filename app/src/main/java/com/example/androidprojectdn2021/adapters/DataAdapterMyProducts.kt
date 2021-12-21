package com.example.androidprojectdn2021.adapters

import android.content.Context
import android.content.res.loader.ResourcesLoader
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.Resource
import com.example.androidprojectdn2021.R
import com.example.androidprojectdn2021.modelclasses.Product

class DataAdapterMyProducts(
    private var list: ArrayList<Product>,
    private val context: Context,
    private val onItemClickListener: OnItemClickListener,
) :
    RecyclerView.Adapter<DataAdapterMyProducts.DataViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    // 1. user defined ViewHolder type - Embedded class!
    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val textViewName: TextView = itemView.findViewById(R.id.productNameTVOwnProductLayout)
        val textViewPrice: TextView = itemView.findViewById(R.id.productPriceTVOwnProductLayout)
        val textViewSeller: TextView = itemView.findViewById(R.id.userNameTVOwnProductLayout)
        val imageView: ImageView = itemView.findViewById(R.id.productImageOwnProductLayout)
        val textViewStatus: TextView = itemView.findViewById(R.id.statusTVOwnProductLayout)
        val statusIV: AppCompatImageView = itemView.findViewById(R.id.statusImageIV)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val currentPosition = this.adapterPosition
            onItemClickListener.onItemClick(currentPosition)
        }
    }

    // 2. Called only a few times = number of items on screen + a few more ones
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataAdapterMyProducts.DataViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.own_product_layout, parent, false)
        return DataViewHolder(itemView)
    }

    // 3. Called many times, when we scroll the list
    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val currentItem = list[position]
        holder.textViewSeller.text = currentItem.username
        holder.textViewName.text = currentItem.title
        holder.textViewPrice.text =
            currentItem.price_per_unit + " " + currentItem.price_type + "/" + currentItem.amount_type
        holder.textViewStatus.text = currentItem.username
        if (currentItem.is_active) {
            holder.textViewStatus.text = "Active"
            holder.statusIV.setBackgroundResource(R.drawable.ic_check)
        }
        else {
            holder.textViewStatus.text = "Inactive"
            holder.statusIV.setBackgroundResource(R.drawable.ic_non_check)
        }

        val images = currentItem.images
        if (images != null && images.size > 0) {
            Log.d("dnj", "#num_images: ${images.size}")
        }
        Glide.with(this.context)
            .load(R.drawable.ic_baseline_shopping_bag_24)
            .override(200, 200)
            .into(holder.imageView);
    }

    override fun getItemCount() = list.size

    // Update the list
    fun setData(newlist: ArrayList<Product>) {
        list = newlist
    }
}