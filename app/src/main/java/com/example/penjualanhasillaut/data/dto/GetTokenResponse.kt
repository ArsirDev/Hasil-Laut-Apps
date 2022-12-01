package com.example.penjualanhasillaut.data.dto

import com.google.gson.annotations.SerializedName

data class GetTokenResponse(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class Data(

	@field:SerializedName("device_token")
	val deviceToken: String
)
