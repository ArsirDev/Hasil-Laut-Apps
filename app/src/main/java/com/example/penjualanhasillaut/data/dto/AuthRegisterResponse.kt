package com.example.penjualanhasillaut.data.dto

import com.google.gson.annotations.SerializedName

data class AuthRegisterResponse(

	@field:SerializedName("data")
	val dataAuthRegisterItem: DataAuthRegisterItem,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class DataAuthRegisterItem(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("token")
	val token: String
)
