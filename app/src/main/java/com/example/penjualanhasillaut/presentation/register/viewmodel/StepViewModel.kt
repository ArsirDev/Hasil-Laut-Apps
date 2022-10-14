package com.example.penjualanhasillaut.presentation.register.viewmodel

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.penjualanhasillaut.data.dto.AuthRegisterResponse
import com.example.penjualanhasillaut.domain.repository.AppsRepository
import com.example.penjualanhasillaut.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StepViewModel @Inject constructor(
    private val repository: AppsRepository
) : ViewModel() {

    private val _registerData = MutableLiveData<Result<AuthRegisterResponse>>()

    private val _uiEvent = MutableSharedFlow<String>()

    val uiEvent get() = _uiEvent.asSharedFlow()

    fun onStepOneValidation(name: String, email: String, alamat: String, onValidation: () -> Unit) = viewModelScope.launch {
        if (name.isEmpty() || email.isEmpty() || alamat.isEmpty()) {
            _uiEvent.emit("Data Tidak boleh Kosong")
            return@launch
        }

        val emailsPattern = Patterns.EMAIL_ADDRESS
        if (!emailsPattern.matcher(email).matches()) {
            _uiEvent.emit("Email tidak valid")
            return@launch
        }

        onValidation()
    }

    fun onStepTwoValidation(name: String, email: String, alamat: String, status: String, phone: String, password: String, confirm_password: String) = viewModelScope.launch {
        if (phone.isEmpty() || password.isEmpty() || confirm_password.isEmpty()) {
            _uiEvent.emit("Data Tidak boleh Kosong")
            return@launch
        }

        if (password != confirm_password) {
            _uiEvent.emit("Password tidak sama")
            return@launch
        }

        val phonePatterns = Patterns.PHONE
        if (!phonePatterns.matcher(phone).matches()) {
            _uiEvent.emit("Phone tidak valid")
            return@launch
        }

        register(
            name,
            email,
            alamat,
            status,
            phone,
            password,
            confirm_password
        )

    }

    private fun register(
        name: String,
        email: String,
        address: String,
        status: String,
        number_phone: String,
        password: String,
        password_confirmation: String
    ) = viewModelScope.launch {
        val register = repository.getRegister(
            name,
            email,
            address,
            status,
            number_phone,
            password,
            password_confirmation
        )
        _registerData.postValue(register)
    }

    fun getRegister():LiveData<Result<AuthRegisterResponse>> = _registerData
}