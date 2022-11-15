package com.example.penjualanhasillaut.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Keranjang(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val image: String,
    var amount: String,
    val address: String,
    val productName: String,
    val idProduct: Int,
    val userId: String,
    val qty: String,
    val ownerProduct: String,
    val payerName: String,
    val email: String
)