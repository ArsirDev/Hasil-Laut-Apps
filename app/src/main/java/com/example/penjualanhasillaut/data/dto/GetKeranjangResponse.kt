package com.example.penjualanhasillaut.data.dto

import com.google.gson.annotations.SerializedName

data class GetKeranjangResponse(

	@field:SerializedName("data")
	val dataGetKeranjangItem: List<DataGetKeranjangItem>,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class DataGetKeranjangItem(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("amount")
	var amount: String,

	@field:SerializedName("address")
	val address: String,

	@field:SerializedName("created_at")
	val createdAt: Any,

	@field:SerializedName("product_name")
	val productName: String,

	@field:SerializedName("id_product")
	val idProduct: Int,

	@field:SerializedName("updated_at")
	val updatedAt: Any,

	@field:SerializedName("user_id")
	val userId: String,

	@field:SerializedName("qty")
	val qty: String,

	@field:SerializedName("owner_product")
	val ownerProduct: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("payer_name")
	val payerName: String,

	@field:SerializedName("email")
	val email: String
)
