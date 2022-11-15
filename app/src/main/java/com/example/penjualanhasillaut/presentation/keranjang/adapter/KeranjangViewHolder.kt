package com.example.penjualanhasillaut.presentation.keranjang.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.penjualanhasillaut.data.dto.DataGetKeranjangItem
import com.example.penjualanhasillaut.databinding.KeranjangItemLayoutBinding
import com.example.penjualanhasillaut.utils.formatCurrency
import com.example.penjualanhasillaut.utils.loadImage
import java.util.*

class KeranjangViewHolder(
    val binding: KeranjangItemLayoutBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bind(dataGetKeranjangItem: DataGetKeranjangItem) {
        with(binding) {
            ivBarang.loadImage(dataGetKeranjangItem.image)
            tvNamaBarang.text = dataGetKeranjangItem.productName.uppercase(Locale.getDefault())
            tvQty.text = String.format("%s Pcs", dataGetKeranjangItem.qty)
            tvPrize.text = String.format("Rp.%s", dataGetKeranjangItem.amount.formatCurrency())
        }
    }
}