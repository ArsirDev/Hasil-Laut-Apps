package com.example.penjualanhasillaut.data.api

import com.example.penjualanhasillaut.constant.EndPoint.DELETE_KERANJANG
import com.example.penjualanhasillaut.constant.EndPoint.DELETE_KERANJANG_BY_ID
import com.example.penjualanhasillaut.constant.EndPoint.DELETE_TRANSAKSI
import com.example.penjualanhasillaut.constant.EndPoint.DETAIL
import com.example.penjualanhasillaut.constant.EndPoint.GET_KERANJANG
import com.example.penjualanhasillaut.constant.EndPoint.GET_TOKEN
import com.example.penjualanhasillaut.constant.EndPoint.GET_TRANSAKSI
import com.example.penjualanhasillaut.constant.EndPoint.INPUT
import com.example.penjualanhasillaut.constant.EndPoint.LOGIN
import com.example.penjualanhasillaut.constant.EndPoint.PUSH_NOTIFICATION
import com.example.penjualanhasillaut.constant.EndPoint.REGISTER
import com.example.penjualanhasillaut.constant.EndPoint.SAVE_TOKEN
import com.example.penjualanhasillaut.constant.EndPoint.SEARCH
import com.example.penjualanhasillaut.constant.EndPoint.SET_KERANJANG
import com.example.penjualanhasillaut.constant.EndPoint.TRANSAKSI
import com.example.penjualanhasillaut.constant.EndPoint.USERS
import com.example.penjualanhasillaut.data.dto.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiInterface {

    @FormUrlEncoded
    @POST(LOGIN)
    suspend fun getLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<AuthLoginResponse>

    @FormUrlEncoded
    @POST(REGISTER)
    suspend fun getRegister(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("address") address: String,
        @Field("status") status: String,
        @Field("number_phone") number_phone: String,
        @Field("password") password: String,
        @Field("password_confirmation") password_confirmation: String
    ): Response<AuthRegisterResponse>

    @GET(SEARCH)
    suspend fun getSearch(
        @Query("product_name") product_name: String
    ): Response<SearchResponse>

    @Multipart
    @POST(INPUT)
    suspend fun getInput(
        @Part("product_name") product_name: RequestBody,
        @Part("qty") qty: Int,
        @Part("price") price: Int,
        @Part("type") type: RequestBody,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody
    ): Response<InputResponse>

    @GET(DETAIL)
    suspend fun getDetail(
        @Query("id") id: Int
    ): Response<DetailResponse>

    @GET(USERS)
    suspend fun getUsers(
        @Query("id") id: Int
    ): Response<AuthLoginResponse>

    @FormUrlEncoded
    @POST(TRANSAKSI)
    suspend fun getTransaksi(
        @Field("id_product") id_product: Int,
        @Field("user_id") user_id: String,
        @Field("product_name") product_name: String,
        @Field("email") email: String,
        @Field("address") address: String,
        @Field("owner_product") owner_product: String,
        @Field("amount") amount: Int,
        @Field("qty") qty: Int,
        @Field("total_item") total_item: Int,
        @Field("image") image: String,
        @Field("description") description: String
    ): Response<InvoiceResponse>

    @GET(GET_TRANSAKSI)
    suspend fun getAllTransaksiById(): Response<TransaksiResponse>

    @GET(DELETE_TRANSAKSI)
    suspend fun deleteTransaksi(
        @Query("id") id: Int
    ): Response<GeneralResponse>

    @FormUrlEncoded
    @POST(SET_KERANJANG)
    suspend fun setKeranjang(
        @Field("id_product") id_product: Int,
        @Field("user_id") user_id: String,
        @Field("product_name") product_name: String,
        @Field("email") email: String,
        @Field("address") address: String,
        @Field("owner_product") owner_product: String,
        @Field("amount") amount: Int,
        @Field("qty") qty: Int,
        @Field("image") image: String,
        @Field("description") description: String
    ): Response<GeneralResponse>

    @GET(GET_KERANJANG)
    suspend fun getKeranjang(): Response<GetKeranjangResponse>

    @GET(DELETE_KERANJANG_BY_ID)
    suspend fun deleteKeranjangById(
        @Query("id") Id: Int
    ): Response<GeneralResponse>

    @GET(DELETE_KERANJANG)
    suspend fun deleteKeranjang(): Response<GeneralResponse>

    @POST(SAVE_TOKEN)
    suspend fun saveToken(
        @Query("device_token") token: String
    ): Response<GeneralResponse>

    @GET(GET_TOKEN)
    suspend fun getToken(
        @Query("id") id: Int
    ): Response<GetTokenResponse>


    @POST(PUSH_NOTIFICATION)
    suspend fun pushNotification(
        @Query("device_token") device_token: String,
        @Query("body") body: String,
    ): Response<NotificationResponse>
}
