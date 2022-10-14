package com.example.penjualanhasillaut.presentation.login.viewmodel

import android.util.Patterns
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
class LoginViewModel @Inject constructor(
    private val repository: AppsRepository
): ViewModel() {

    private val _loginData = MutableLiveData<Result<AuthLoginResponse>>()

    fun validation(
        email: String,
        password: String
    ) {
        if (email.isEmpty() || password.isEmpty()){
            _loginData.postValue(Result.Error(null, "Data tidak boleh kosong"))
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _loginData.postValue(Result.Error(null,"Email tidak valid"))
            return
        }

        login(email, password)
    }

    private fun login(
        email: String,
        password: String
    ) = viewModelScope.launch {
        _loginData.postValue(Result.Loading())

        val login = repository.getLogin(email, password)
        _loginData.postValue(login)
    }

    fun getLogin():LiveData<Result<AuthLoginResponse>> = _loginData
}