package com.example.penjualanhasillaut.domain.adapter.sold

import androidx.recyclerview.widget.RecyclerView
import com.example.penjualanhasillaut.data.dto.DataTransaksiItem
import com.example.penjualanhasillaut.databinding.SoldItemLayoutBinding
import com.example.penjualanhasillaut.utils.loadImage

class SoldViewHolder(
    val binding: SoldItemLayoutBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: DataTransaksiItem) {
        with(binding) {
            ivProduct.loadImage(item.image)
            tvProduct.text = item.productName
            tvSellerName.text = item.ownerProduct
            tvBoughtName.text = item.payerName
            tvQty.text = item.totalItem.toString()
            tvPrice.text = item.amount.toString()
            tvDescription.text = String.format("Product ${item.productName} Telah di beli oleh ${item.payerName}")
        }
    }
}