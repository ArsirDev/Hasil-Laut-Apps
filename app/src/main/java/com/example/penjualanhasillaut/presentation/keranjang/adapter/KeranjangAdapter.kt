package com.example.penjualanhasillaut.presentation.keranjang.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.penjualanhasillaut.data.dto.DataGetKeranjangItem
import com.example.penjualanhasillaut.databinding.KeranjangItemLayoutBinding

class KeranjangAdapter: RecyclerView.Adapter<KeranjangViewHolder>() {

    private val differCallback = object: DiffUtil.ItemCallback<DataGetKeranjangItem>() {
        override fun areItemsTheSame(
            oldItem: DataGetKeranjangItem,
            newItem: DataGetKeranjangItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: DataGetKeranjangItem,
            newItem: DataGetKeranjangItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeranjangViewHolder {
        return KeranjangViewHolder(KeranjangItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: KeranjangViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    companion object {
        fun instance() = KeranjangAdapter()
    }
}