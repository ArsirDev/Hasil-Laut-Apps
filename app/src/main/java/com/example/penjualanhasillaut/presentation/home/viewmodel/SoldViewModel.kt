package com.example.penjualanhasillaut.presentation.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.penjualanhasillaut.data.dto.GeneralResponse
import com.example.penjualanhasillaut.data.dto.TransaksiResponse
import com.example.penjualanhasillaut.domain.repository.AppsRepository
import javax.inject.Inject
import com.example.penjualanhasillaut.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@HiltViewModel
class SoldViewModel @Inject constructor(
    private val repository: AppsRepository
): ViewModel() {

    private val _sold = MutableLiveData<Result<TransaksiResponse>>()

    private val _delete = MutableLiveData<Result<GeneralResponse>>()

    init {
        fetchSold()
    }

    private fun fetchSold() = viewModelScope.launch {
        _sold.postValue(Result.Loading())

        val sold = repository.getAllTransaksiById()

        _sold.postValue(sold)
    }

    fun getSold(): LiveData<Result<TransaksiResponse>> = _sold

    fun deleteTransaksi(id: Int) = viewModelScope.launch {
        _delete.postValue(Result.Loading())

        val delete = repository.deleteTransaksi(id)

        _delete.postValue(delete)
    }

    fun getDeleteTransaksi(): LiveData<Result<GeneralResponse>> = _delete
}