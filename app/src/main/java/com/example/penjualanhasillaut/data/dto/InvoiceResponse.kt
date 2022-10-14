package com.example.penjualanhasillaut.data.dto

import com.google.gson.annotations.SerializedName

data class InvoiceResponse(

	@field:SerializedName("data")
	val dataInvoice: DataInvoice,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class AvailableDirectDebitsItem(

	@field:SerializedName("direct_debit_type")
	val directDebitType: String
)

data class AvailableEwalletsItem(

	@field:SerializedName("ewallet_type")
	val ewalletType: String
)

data class AvailableBanksItem(

	@field:SerializedName("bank_code")
	val bankCode: String,

	@field:SerializedName("bank_branch")
	val bankBranch: String,

	@field:SerializedName("transfer_amount")
	val transferAmount: Int,

	@field:SerializedName("account_holder_name")
	val accountHolderName: String,

	@field:SerializedName("identity_amount")
	val identityAmount: Int,

	@field:SerializedName("collection_type")
	val collectionType: String
)

data class DataInvoice(

	@field:SerializedName("amount")
	val amount: Int,

	@field:SerializedName("available_paylaters")
	val availablePaylaters: List<Any>,

	@field:SerializedName("available_banks")
	val availableBanks: List<AvailableBanksItem>,

	@field:SerializedName("available_ewallets")
	val availableEwallets: List<AvailableEwalletsItem>,

	@field:SerializedName("available_retail_outlets")
	val availableRetailOutlets: List<AvailableRetailOutletsItem>,

	@field:SerializedName("created")
	val created: String,

	@field:SerializedName("expiry_date")
	val expiryDate: String,

	@field:SerializedName("merchant_name")
	val merchantName: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("external_id")
	val externalId: String,

	@field:SerializedName("available_direct_debits")
	val availableDirectDebits: List<AvailableDirectDebitsItem>,

	@field:SerializedName("merchant_profile_picture_url")
	val merchantProfilePictureUrl: String,

	@field:SerializedName("user_id")
	val userId: String,

	@field:SerializedName("should_send_email")
	val shouldSendEmail: Boolean,

	@field:SerializedName("should_exclude_credit_card")
	val shouldExcludeCreditCard: Boolean,

	@field:SerializedName("currency")
	val currency: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("updated")
	val updated: String,

	@field:SerializedName("invoice_url")
	val invoiceUrl: String,

	@field:SerializedName("status")
	val status: String
)

data class AvailableRetailOutletsItem(

	@field:SerializedName("retail_outlet_name")
	val retailOutletName: String
)
