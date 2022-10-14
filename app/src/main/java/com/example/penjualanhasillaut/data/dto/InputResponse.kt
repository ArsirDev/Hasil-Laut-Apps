package com.example.penjualanhasillaut.data.dto

import com.google.gson.annotations.SerializedName

data class InputResponse(

    @field:SerializedName("data")
	val data: DataInput,

    @field:SerializedName("success")
	val success: Boolean,

    @field:SerializedName("message")
	val message: String
)

data class DataInput(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("product_name")
	val productName: String,

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("user_id")
	val userId: Int,

	@field:SerializedName("user_name")
	val userName: String,

	@field:SerializedName("price")
	val price: String,

	@field:SerializedName("qty")
	val qty: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("type")
	val type: String
)
