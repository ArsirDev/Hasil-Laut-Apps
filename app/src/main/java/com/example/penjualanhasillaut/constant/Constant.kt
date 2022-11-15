package com.example.penjualanhasillaut.constant

object EndPoint {
    const val LOGIN = "api/login"
    const val REGISTER = "api/register"
    const val SEARCH = "api/search"
    const val INPUT = "api/insert-hasil"
    const val DETAIL = "api/detail"
    const val USERS = "api/profil"
    const val TRANSAKSI = "api/transaksi"
    const val SET_KERANJANG = "api/set-cart"
    const val GET_KERANJANG = "api/get-cart"
    const val DELETE_KERANJANG_BY_ID = "api/delete-item-cart"
    const val DELETE_KERANJANG = "api/delete-cart"
}

object AUTH {
    const val AUTH_HEADER = "Authorization"
}

object MESSAGE {
    const val STATUS_SUCCESS = "success"
    const val STATUS_ERROR = "error"
}

object SESSION {
    const val ID = "ID"
    const val EMAIL = "EMAIL"
    const val NAME = "NAME"
    const val ADDRESS = "ADDRESS"
    const val STATUS = "STATUS"
    const val NUMBER_PHONE = "NUMBER_PHONE"
    const val ACCESS_TOKEN = "ACCESS_TOKEN"
    const val DATA_LOGIN = "DATA_LOGIN"

}

object PASSINGDATA {
    const val REGISTER_DATA ="Data_Step_On"
}

object STATUS {
    const val PENYALUR = "Penyalur"
    const val KONSUMEN = "Konsumen"
    const val PREMIUM = "Premium"
    const val REGULAR = "Regular"
}

object TRANSAKSI {
    const val USERID = "user_id"
    const val PRODUCTNAME = "product_name"
    const val EMAILTRANSAKSI = "email"
    const val ADDRESSTRANSAKSI = "address"
    const val OWNERPRODUCT = "owner_product"
    const val AMONT = "amount"
    const val QTY = "qty"
    const val IMAGE = "image"
    const val DESCRIPTION = "description"
}

