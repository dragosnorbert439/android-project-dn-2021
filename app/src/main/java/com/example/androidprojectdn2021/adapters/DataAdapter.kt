package com.example.androidprojectdn2021.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidprojectdn2021.R
import com.example.androidprojectdn2021.modelclasses.Product

class DataAdapter(
    private var list: ArrayList<Product>,
    private val context: Context,
    private val listener: OnItemClickListener,
    private val listener2: OnItemLongClickListener,
    private val listener3: OnOrderButtonClickListener
) :
    RecyclerView.Adapter<DataAdapter.DataViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(position: Int)
    }

    interface OnOrderButtonClickListener {
        fun onButtonClick(position: Int)
    }

    // 1. user defined ViewHolder type - Embedded class!
    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener, View.OnLongClickListener {
        val textView_name: TextView = itemView.findViewById(R.id.productNameTVProductLayout)
        val textView_price: TextView = itemView.findViewById(R.id.productPriceTVProductLayout)
        val textView_seller: TextView = itemView.findViewById(R.id.userNameTVProductLayout)
        val imageView: ImageView = itemView.findViewById(R.id.productImageProductLayout)
        val orderButton: AppCompatButton = itemView.findViewById(R.id.orderButtonProductLayout)

        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)

            orderButton.setOnClickListener {
                val position = this.adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener3.onButtonClick(position)
                }
            }
        }

        override fun onClick(p0: View?) {
            val currentPosition = this.adapterPosition
            listener.onItemClick(currentPosition)
        }

        override fun onLongClick(p0: View?): Boolean {
            val currentPosition = this.adapterPosition
            listener2.onItemLongClick(currentPosition)
            return true
        }

        

    }

    // 2. Called only a few times = number of items on screen + a few more ones
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.product_layout, parent, false)
        return DataViewHolder(itemView)
    }

    // 3. Called many times, when we scroll the list
    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val currentItem = list[position]
        holder.textView_name.text = currentItem.title
        holder.textView_price.text =
            currentItem.price_per_unit + " " + currentItem.price_type + "/" + currentItem.amount_type
        holder.textView_seller.text = currentItem.username
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