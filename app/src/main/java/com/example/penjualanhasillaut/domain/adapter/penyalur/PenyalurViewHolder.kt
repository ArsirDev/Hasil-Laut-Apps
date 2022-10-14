package com.example.penjualanhasillaut.domain.adapter.penyalur

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.penjualanhasillaut.R
import com.example.penjualanhasillaut.data.dto.DataSearchItem
import com.example.penjualanhasillaut.databinding.SearchItemLayoutBinding
import com.example.penjualanhasillaut.utils.formatCurrency
import com.example.penjualanhasillaut.utils.loadImage
import com.example.penjualanhasillaut.utils.removeQuote
import com.example.penjualanhasillaut.utils.removeView
import com.example.penjualanhasillaut.utils.showView

class PenyalurViewHolder(
    private val binding: SearchItemLayoutBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bind(searchItem: DataSearchItem) {
        with(binding) {
            soldOutCondition(searchItem.qty)
            ivProduct.loadImage(searchItem.image)
            tvProductName.text = searchItem.productName
            conditionType(searchItem.type)
            tvType.text = searchItem.type
            tvName.text = searchItem.userName
            tvPrice.text = searchItem.price.toString().formatCurrency()
        }
    }

    private fun soldOutCondition(qty: Int) {
        if (qty <= 0) {
            binding.lySoldOut.showView()
            binding.ivDelete.showView()
            return
        } else {
            binding.lySoldOut.removeView()
            binding.ivDelete.removeView()
            return
        }
    }

    private fun conditionType(type: String) {
        if (type.removeQuote().contains("Premium")) {
            binding.cvType.setCardBackgroundColor(itemView.context.getColor(R.color.orangeBadge))
            return
        } else if (type.removeQuote().contains("Regular")) {
            binding.cvType.setCardBackgroundColor(itemView.context.getColor(R.color.dark))
            binding.ivType.visibility = View.GONE
            return
        }
    }
}