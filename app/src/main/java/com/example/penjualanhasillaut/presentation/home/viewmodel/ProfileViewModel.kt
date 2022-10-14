package com.example.penjualanhasillaut.presentation.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.penjualanhasillaut.data.dto.AuthLoginResponse
import com.example.penjualanhasillaut.domain.repository.AppsRepository
import com.example.penjualanhasillaut.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: AppsRepository
): ViewModel() {

    private val _users = MutableLiveData<Result<AuthLoginResponse>>()

    fun setUsers(
        id: Int
    ) = viewModelScope.launch {
        _users.postValue(Result.Loading())

        val users = repository.getUsers(id)
        _users.postValue(users)
    }

    fun getUsers(): LiveData<Result<AuthLoginResponse>> = _users
}