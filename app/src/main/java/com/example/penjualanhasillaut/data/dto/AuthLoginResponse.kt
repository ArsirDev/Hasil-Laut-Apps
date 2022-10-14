package com.example.penjualanhasillaut.data.dto

import com.google.gson.annotations.SerializedName

data class AuthLoginResponse(

	@field:SerializedName("data")
	val dataLogin: DataLogin,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class DataLogin(

	@field:SerializedName("address")
	val address: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("token")
	val token: String,

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("number_phone")
	val numberPhone: String
)
