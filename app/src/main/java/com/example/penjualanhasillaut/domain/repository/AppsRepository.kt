package com.example.penjualanhasillaut.domain.repository

import com.example.penjualanhasillaut.data.dto.AuthLoginResponse
import com.example.penjualanhasillaut.data.dto.AuthRegisterResponse
import com.example.penjualanhasillaut.data.dto.DetailResponse
import com.example.penjualanhasillaut.data.dto.GeneralResponse
import com.example.penjualanhasillaut.data.dto.GetKeranjangResponse
import com.example.penjualanhasillaut.data.dto.InputResponse
import com.example.penjualanhasillaut.data.dto.InvoiceResponse
import com.example.penjualanhasillaut.data.dto.SearchResponse
import com.example.penjualanhasillaut.domain.model.Keranjang
import com.example.penjualanhasillaut.utils.Result
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Field
import java.io.File

interface AppsRepository {

    suspend fun getLogin(email: String, password: String): Result<AuthLoginResponse>

    suspend fun getRegister(
        name: String,
        email: String,
        address: String,
        status: String,
        number_phone: String,
        password: String,
        password_confirmation: String
    ): Result<AuthRegisterResponse>

    suspend fun getSearch(
        product_name: String
    ): Result<SearchResponse>

    suspend fun getInput(
        product_name: String,
        qty: Int,
        price: Int,
        type: String,
        image: File,
        description: String
    ): Result<InputResponse>

    suspend fun getDetail(
        id: Int
    ): Result<DetailResponse>

    suspend fun getUsers(
        id: Int
    ): Result<AuthLoginResponse>

    suspend fun getTransaksi(
        id_product: Int,
        user_id: String,
        product_name: String,
        email: String,
        address: String,
        owner_product: String,
        amount: Int,
        qty: Int,
        image: String,
        description: String
    ): Result<InvoiceResponse>

    suspend fun setKeranjang(
        id_product: Int,
        user_id: String,
        product_name: String,
        email: String,
        address: String,
        owner_product: String,
        amount: Int,
        qty: Int,
        image: String,
        description: String
    ): Result<GeneralResponse>

    suspend fun getKeranjang(): Result<GetKeranjangResponse>

    suspend fun deleteKeranjangById(id: Int): Result<GeneralResponse>

    suspend fun deleteKeranjang(): Result<GeneralResponse>

}