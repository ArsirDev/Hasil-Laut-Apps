package com.example.penjualanhasillaut.data.dto

import com.google.gson.annotations.SerializedName

data class TransaksiResponse(

	@field:SerializedName("data")
	val data: List<DataTransaksiItem>,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class DataTransaksiItem(

	@field:SerializedName("total_item")
	val totalItem: Int,

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("amount")
	val amount: Int,

	@field:SerializedName("address")
	val address: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("created_at")
	val createdAt: Any,

	@field:SerializedName("external_id")
	val externalId: String,

	@field:SerializedName("product_name")
	val productName: String,

	@field:SerializedName("id_product")
	val idProduct: Int,

	@field:SerializedName("updated_at")
	val updatedAt: Any,

	@field:SerializedName("user_id")
	val userId: String,

	@field:SerializedName("qty")
	val qty: Int,

	@field:SerializedName("owner_product")
	val ownerProduct: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("payer_name")
	val payerName: String,

	@field:SerializedName("email")
	val email: String
)
