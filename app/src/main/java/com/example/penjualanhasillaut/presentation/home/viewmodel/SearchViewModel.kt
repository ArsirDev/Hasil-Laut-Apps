package com.example.penjualanhasillaut.presentation.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.penjualanhasillaut.data.dto.SearchResponse
import com.example.penjualanhasillaut.domain.repository.AppsRepository
import com.example.penjualanhasillaut.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: AppsRepository
): ViewModel() {

    private val _search = MutableLiveData<Result<SearchResponse>>()

    fun fetchSearch(
        search: String
    ) = viewModelScope.launch {
        _search.postValue(Result.Loading())

        val query = repository.getSearch(search)
        _search.postValue(query)
    }

    fun getSearch(): LiveData<Result<SearchResponse>> = _search
}