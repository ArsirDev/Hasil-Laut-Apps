package com.example.penjualanhasillaut.utils

sealed class UiEvent {
    data class ShowSnackbar(val message: String): UiEvent()
}