package com.example.penjualanhasillaut.presentation.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.penjualanhasillaut.data.dto.DetailResponse
import com.example.penjualanhasillaut.data.dto.InvoiceResponse
import com.example.penjualanhasillaut.domain.repository.AppsRepository
import com.example.penjualanhasillaut.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: AppsRepository
): ViewModel() {

    private val _detail = MutableLiveData<Result<DetailResponse>>()


    fun setDetail(id: Int) = viewModelScope.launch {
        _detail.postValue(Result.Loading())

        val detail = repository.getDetail(id)
        _detail.postValue(detail)
    }

    fun getDetail(): LiveData<Result<DetailResponse>> = _detail

}