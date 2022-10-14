package com.example.penjualanhasillaut.data.repository

import com.example.penjualanhasillaut.data.api.ApiInterface
import com.example.penjualanhasillaut.data.dto.AuthLoginResponse
import com.example.penjualanhasillaut.data.dto.AuthRegisterResponse
import com.example.penjualanhasillaut.data.dto.DetailResponse
import com.example.penjualanhasillaut.data.dto.GeneralResponse
import com.example.penjualanhasillaut.data.dto.GetKeranjangResponse
import com.example.penjualanhasillaut.data.dto.InputResponse
import com.example.penjualanhasillaut.data.dto.InvoiceResponse
import com.example.penjualanhasillaut.domain.repository.AppsRepository
import com.example.penjualanhasillaut.utils.ResponseHandler
import com.example.penjualanhasillaut.utils.Result
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

class AppsRepositoryImpl @Inject constructor(
    private val apiService: ApiInterface,
    private val responseHandler: ResponseHandler
) : AppsRepository {

    override suspend fun getLogin(email: String, password: String) =
        responseHandler.handleResponse {
            apiService.getLogin(email, password)
        }

    override suspend fun getRegister(
        name: String,
        email: String,
        address: String,
        status: String,
        number_phone: String,
        password: String,
        password_confirmation: String
    ): Result<AuthRegisterResponse> = responseHandler.handleResponse {
        apiService.getRegister(
            name,
            email,
            address,
            status,
            number_phone,
            password,
            password_confirmation
        )
    }

    override suspend fun getSearch(product_name: String) = responseHandler.handleResponse {
        apiService.getSearch(
            product_name
        )
    }

    override suspend fun getInput(
        product_name: String,
        qty: Int,
        price: Int,
        type: String,
        image: File,
        description: String
    ): Result<InputResponse> = responseHandler.handleResponse {
        val requestBody = image.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val multipartBody = MultipartBody.Part.createFormData("image", image.name, requestBody)


        val productRequest = product_name.toRequestBody("text/plain".toMediaTypeOrNull())
        val typeRequest = type.toRequestBody("text/plain".toMediaTypeOrNull())
        val descriptRequest = description.toRequestBody("text/plain".toMediaTypeOrNull())
        apiService.getInput(
            productRequest,
            qty,
            price,
            typeRequest,
            multipartBody,
            descriptRequest
        )
    }

    override suspend fun getDetail(id: Int): Result<DetailResponse> =
        responseHandler.handleResponse {
            apiService.getDetail(id)
        }

    override suspend fun getUsers(id: Int): Result<AuthLoginResponse> =
        responseHandler.handleResponse {
            apiService.getUsers(id)
        }

    override suspend fun getTransaksi(
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
    ): Result<InvoiceResponse> = responseHandler.handleResponse {
        apiService.getTransaksi(
            id_product,
            user_id,
            product_name,
            email,
            address,
            owner_product,
            amount,
            qty,
            image,
            description,
        )
    }

    override suspend fun setKeranjang(
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
    ): Result<GeneralResponse> = responseHandler.handleResponse {
        apiService.setKeranjang(
            id_product,
            user_id,
            product_name,
            email,
            address,
            owner_product,
            amount,
            qty,
            image,
            description
        )
    }

    override suspend fun getKeranjang(): Result<GetKeranjangResponse> = responseHandler.handleResponse {
        apiService.getKeranjang()
    }

    override suspend fun deleteKeranjang(): Result<GeneralResponse> = responseHandler.handleResponse {
        apiService.deleteKeranjang()
    }
}