package com.example.penjualanhasillaut.presentation.transaksi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.penjualanhasillaut.data.dto.InvoiceResponse
import com.example.penjualanhasillaut.domain.repository.AppsRepository
import com.example.penjualanhasillaut.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransaksiViewModel @Inject constructor(
    private val repository: AppsRepository
): ViewModel(){

    private val _transaksi = MutableLiveData<Result<InvoiceResponse>>()

    fun setTransaksi(
        id_product: Int,
        user_id: String,
        product_name: String,
        email: String,
        address: String,
        owner_product: String,
        amount: Int,
        qty: Int,
        total_item: Int,
        image: String,
        description: String
    ) = viewModelScope.launch {
        _transaksi.postValue(Result.Loading())

        val transaksi = repository.getTransaksi(
            id_product,
            user_id,
            product_name,
            email,
            address,
            owner_product,
            amount,
            qty,
            total_item,
            image,
            description
        )

        _transaksi.postValue(transaksi)
    }

    fun getTransaksi(): LiveData<Result<InvoiceResponse>> = _transaksi
}