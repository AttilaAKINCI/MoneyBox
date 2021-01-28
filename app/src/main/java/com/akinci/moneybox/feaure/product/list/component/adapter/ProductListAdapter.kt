package com.akinci.moneybox.feaure.product.list.component.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.akinci.moneybox.common.component.GlideApp
import com.akinci.moneybox.databinding.RowProductBinding
import com.akinci.moneybox.feaure.product.list.data.output.ProductResponse

class ProductListAdapter(private val clickListener: (Int) -> Unit) : ListAdapter<ProductResponse, RecyclerView.ViewHolder>(ProductDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ProductViewHolder(RowProductBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        if(holder is ProductViewHolder) { holder.bind(item, clickListener) }
    }

    class ProductViewHolder(val binding: RowProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ProductResponse, clickListener: (Int) -> Unit) {
            // fill row instances..
            binding.productCardView.setOnClickListener { clickListener.invoke(data.Id) }
            binding.data = data

            data.Product.LogoUrl?.let {
                binding.productImage.visibility = View.VISIBLE
                GlideApp.with(binding.productImage.context)
                        .load(it)
                        .fitCenter()
                        .into(binding.productImage)
            }

            binding.executePendingBindings()
        }
    }

    class ProductDiffCallback : DiffUtil.ItemCallback<ProductResponse>() {
        override fun areItemsTheSame(oldItem: ProductResponse, newItem: ProductResponse): Boolean {
            return oldItem.Id == newItem.Id
        }

        override fun areContentsTheSame(oldItem: ProductResponse, newItem: ProductResponse): Boolean {
            return oldItem == newItem
        }
    }
}