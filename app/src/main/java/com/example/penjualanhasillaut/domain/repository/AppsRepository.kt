package com.example.penjualanhasillaut.domain.repository

import com.example.penjualanhasillaut.data.dto.*
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
        total_item: Int,
        image: String,
        description: String
    ): Result<InvoiceResponse>

    suspend fun getAllTransaksiById(): Result<TransaksiResponse>

    suspend fun deleteTransaksi(id: Int): Result<GeneralResponse>

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

    suspend fun updateToken()

    suspend fun newToken(token: String)

    suspend fun saveToken(token: String): Result<GeneralResponse>

    suspend fun getToken(id: Int): Result<GetTokenResponse>

    suspend fun deleteToken()

    suspend fun pushNotification(token: String, body: String): Result<NotificationResponse>

}