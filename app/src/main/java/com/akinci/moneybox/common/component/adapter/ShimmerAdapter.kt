package com.akinci.moneybox.common.component.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.akinci.moneybox.databinding.RowShimmerBinding

class ShimmerAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val randomItemCount = (2..4).random() // 2,3 or 4 instance

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ShimmerViewHolder(RowShimmerBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount(): Int {
        return randomItemCount
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ShimmerViewHolder) { holder.bind() }
    }

    class ShimmerViewHolder(val binding: RowShimmerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() { binding.shimmerViewContainer.startShimmer() }
    }

}