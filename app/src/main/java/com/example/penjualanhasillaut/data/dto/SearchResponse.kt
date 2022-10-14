package com.example.penjualanhasillaut.data.dto

import com.google.gson.annotations.SerializedName

data class SearchResponse(

	@field:SerializedName("data")
	val dataItem: List<DataSearchItem>,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class DataSearchItem(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("address")
	val address: String,

	@field:SerializedName("user_name")
	val userName: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("product_name")
	val productName: String,

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("user_id")
	val userId: String,

	@field:SerializedName("price")
	val price: Int,

	@field:SerializedName("qty")
	val qty: Int,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("number_phone")
	val numberPhone: String,

	@field:SerializedName("status")
	val status: String
)
