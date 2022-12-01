package com.example.penjualanhasillaut.constant

object EndPoint {
    const val LOGIN = "api/login"
    const val REGISTER = "api/register"
    const val SEARCH = "api/search"
    const val INPUT = "api/insert-hasil"
    const val DETAIL = "api/detail"
    const val USERS = "api/profil"
    const val TRANSAKSI = "api/transaksi"
    const val GET_TRANSAKSI = "api/get-transaksi"
    const val DELETE_TRANSAKSI = "api/delete-transaksi"
    const val SET_KERANJANG = "api/set-cart"
    const val GET_KERANJANG = "api/get-cart"
    const val DELETE_KERANJANG_BY_ID = "api/delete-item-cart"
    const val DELETE_KERANJANG = "api/delete-cart"
    const val SAVE_TOKEN = "api/save-token"
    const val GET_TOKEN = "api/get-token"
    const val PUSH_NOTIFICATION = "api/send-notification"
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
    const val DEVICE_TOKEN = "DEVICE_TOKEN"
    const val SELLER_DEVICE_TOKEN = "SELLER_DEVICE_TOKEN"
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
    const val TOTAL_ITEM = "total_item"
    const val IMAGE = "image"
    const val DESCRIPTION = "description"
}

object URL {
    const val BASE_URL = "https://3a82-110-232-81-154.ap.ngrok.io/"
}

