package com.example.penjualanhasillaut.presentation.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.penjualanhasillaut.data.dto.InputResponse
import com.example.penjualanhasillaut.domain.repository.AppsRepository
import com.example.penjualanhasillaut.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class InputViewModel @Inject constructor(
    private val repository: AppsRepository
): ViewModel() {

    private val _input = MutableLiveData<Result<InputResponse>>()

    private val _uiEvent = MutableSharedFlow<String>()

    val uiEvent get() = _uiEvent.asSharedFlow()

    fun setInput(
        product_name: String,
        qty: String,
        price: String,
        type: String,
        image: File?,
        description: String
    ) = viewModelScope.launch {
        if (
            product_name.isEmpty() || qty.isEmpty()|| price.isEmpty()||image == null || description.isEmpty()
        ) {
            _uiEvent.emit("Field tidak boleh kosong")
            return@launch
        }

        _input.postValue(Result.Loading())

        val input = repository.getInput(
            product_name,
            qty.toInt(),
            price.toInt(),
            type,
            image,
            description
        )
        _input.postValue(input)
    }

    fun getInput(): LiveData<Result<InputResponse>> = _input
}