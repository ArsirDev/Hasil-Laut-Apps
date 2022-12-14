package com.example.penjualanhasillaut.data.dto

import com.google.gson.annotations.SerializedName

data class GeneralResponse(

	@field:SerializedName("data")
	val data: Any,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)
