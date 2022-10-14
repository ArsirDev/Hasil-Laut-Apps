package com.example.penjualanhasillaut.domain.adapter.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.penjualanhasillaut.data.dto.DataSearchItem
import com.example.penjualanhasillaut.databinding.SearchItemLayoutBinding
import com.example.penjualanhasillaut.utils.setOnClickListenerWithDebounce

class SearchAdapter: RecyclerView.Adapter<SearchViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<DataSearchItem>() {
        override fun areItemsTheSame(oldItem: DataSearchItem, newItem: DataSearchItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DataSearchItem, newItem: DataSearchItem): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(SearchItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.apply {
            bind(differ.currentList[position].also { item ->
                itemView.setOnClickListenerWithDebounce {
                    onItemClickListener?.let { stored ->
                        stored(item.id)
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
        fun instance() = SearchAdapter()
    }
}