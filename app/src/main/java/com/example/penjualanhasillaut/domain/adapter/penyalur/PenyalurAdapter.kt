package com.example.penjualanhasillaut.domain.adapter.penyalur

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.penjualanhasillaut.data.dto.DataSearchItem
import com.example.penjualanhasillaut.databinding.SearchItemLayoutBinding

class PenyalurAdapter: RecyclerView.Adapter<PenyalurViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<DataSearchItem>() {
        override fun areItemsTheSame(oldItem: DataSearchItem, newItem: DataSearchItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DataSearchItem, newItem: DataSearchItem): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PenyalurViewHolder {
        return PenyalurViewHolder(SearchItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PenyalurViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}