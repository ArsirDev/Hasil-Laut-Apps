package com.example.penjualanhasillaut.domain.adapter.sold

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.penjualanhasillaut.data.dto.DataTransaksiItem
import com.example.penjualanhasillaut.databinding.SoldItemLayoutBinding
import com.example.penjualanhasillaut.utils.setOnClickListenerWithDebounce

class SoldAdapter: RecyclerView.Adapter<SoldViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<DataTransaksiItem>() {
        override fun areItemsTheSame(
            oldItem: DataTransaksiItem,
            newItem: DataTransaksiItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: DataTransaksiItem,
            newItem: DataTransaksiItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoldViewHolder {
        return SoldViewHolder(SoldItemLayoutBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: SoldViewHolder, position: Int) {
        with(holder) {
            bind(differ.currentList[position].also { item ->
                binding.ivDelete.setOnClickListenerWithDebounce {
                    onItemClickListener?.let { passing ->
                        passing(item.id)
                    }
                }
            })
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Int) -> Unit)? = null

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClickListener = listener
    }

    companion object {
        fun instance() = SoldAdapter()
    }
}