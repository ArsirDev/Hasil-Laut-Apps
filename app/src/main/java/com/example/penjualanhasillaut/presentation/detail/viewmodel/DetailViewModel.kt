package com.example.penjualanhasillaut.presentation.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.penjualanhasillaut.data.dto.DetailResponse
import com.example.penjualanhasillaut.data.dto.GeneralResponse
import com.example.penjualanhasillaut.data.dto.GetTokenResponse
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

    private val _getToken = MutableLiveData<Result<GetTokenResponse>>()

    fun setDetail(id: Int) = viewModelScope.launch {
        _detail.postValue(Result.Loading())

        val detail = repository.getDetail(id)
        _detail.postValue(detail)
    }

    fun getDetail(): LiveData<Result<DetailResponse>> = _detail

    fun fetchGetToken(id: Int) = viewModelScope.launch {
        _getToken.postValue(Result.Loading())

        val getToken = repository.getToken(id)

        _getToken.postValue(getToken)
    }

    fun getToken(): LiveData<Result<GetTokenResponse>> = _getToken

}