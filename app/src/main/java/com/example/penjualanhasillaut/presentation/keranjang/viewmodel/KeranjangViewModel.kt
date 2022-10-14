package com.example.penjualanhasillaut.presentation.keranjang.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.penjualanhasillaut.data.dto.GeneralResponse
import com.example.penjualanhasillaut.data.dto.GetKeranjangResponse
import com.example.penjualanhasillaut.domain.repository.AppsRepository
import com.example.penjualanhasillaut.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KeranjangViewModel @Inject constructor(
    private val repository: AppsRepository
): ViewModel() {

    private val _setKeranjang = MutableLiveData<Result<GeneralResponse>>()

    private val _getKeranjang = MutableLiveData<Result<GetKeranjangResponse>>()

    private val _deleteKeranjang = MutableLiveData<Result<GeneralResponse>>()

    init {
        viewModelScope.launch {
            _getKeranjang.postValue(repository.getKeranjang())
        }
    }

    fun getKeranjang(): LiveData<Result<GetKeranjangResponse>> = _getKeranjang

    fun fetchKeranjang(
        id_product: Int,
        user_id: String,
        product_name: String,
        email: String,
        address: String,
        owner_product: String,
        amount: Int,
        qty: Int,
        image: String,
        description: String
    ) = viewModelScope.launch {
        _setKeranjang.postValue(Result.Loading())

        val setKeranjang = repository.setKeranjang(
            id_product,
            user_id,
            product_name,
            email,
            address,
            owner_product,
            amount,
            qty,
            image,
            description
        )

        _setKeranjang.postValue(setKeranjang)
    }

    fun setKeranjang(): LiveData<Result<GeneralResponse>> = _setKeranjang

    fun fetchDeleteKeranjang() = viewModelScope.launch {

        val delete = repository.deleteKeranjang()

        _deleteKeranjang.postValue(delete)

    }

    fun getDeleteKeranjang(): LiveData<Result<GeneralResponse>> = _deleteKeranjang
}