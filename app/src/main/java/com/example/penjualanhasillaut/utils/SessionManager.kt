package com.example.penjualanhasillaut.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.penjualanhasillaut.constant.SESSION
import com.example.penjualanhasillaut.constant.SESSION.ACCESS_TOKEN
import com.example.penjualanhasillaut.constant.SESSION.ADDRESS
import com.example.penjualanhasillaut.constant.SESSION.DEVICE_TOKEN
import com.example.penjualanhasillaut.constant.SESSION.EMAIL
import com.example.penjualanhasillaut.constant.SESSION.ID
import com.example.penjualanhasillaut.constant.SESSION.NAME
import com.example.penjualanhasillaut.constant.SESSION.NUMBER_PHONE
import com.example.penjualanhasillaut.constant.SESSION.SELLER_DEVICE_TOKEN
import com.example.penjualanhasillaut.constant.SESSION.STATUS
import javax.inject.Inject

class SessionManager @Inject constructor(
    context: Context
) {
    private val sharedPreferences: SharedPreferences
    private val editor: SharedPreferences.Editor
    private var PRIVATE_MODE = 0

    fun createAuthSession(
        id: Int,
        nama: String?,
        email: String?,
        address: String?,
        status: String?,
        number_phone: String?,
        accessToken: String?,
    ) {
        editor.putInt(ID, id)
        editor.putString(EMAIL, email)
        editor.putString(NAME, nama)
        editor.putString(ADDRESS, address)
        editor.putString(STATUS, status)
        editor.putString(NUMBER_PHONE, number_phone)
        editor.putString(ACCESS_TOKEN, accessToken)
        editor.commit()
    }

    fun updateDeviceToken(
        device_token: String?
    ) {
        editor.putString(DEVICE_TOKEN, device_token)
        editor.commit()
    }

    fun setSellerDeviceToken(
        seller_device_token: String?
    ) {
        editor.putString(SELLER_DEVICE_TOKEN, seller_device_token)
        editor.commit()
    }

    fun logout() {
        editor.remove(ID)
        editor.remove(NAME)
        editor.remove(EMAIL)
        editor.remove(ADDRESS)
        editor.remove(STATUS)
        editor.remove(NUMBER_PHONE)
        editor.remove(ACCESS_TOKEN)
        editor.commit()
    }

    val id: Int
        get() = sharedPreferences.getInt(
            ID,
            0
        )

    val token: String?
        get() = sharedPreferences.getString(
            ACCESS_TOKEN,
            ""
        )


    val device_token: String?
        get() = sharedPreferences.getString(
            DEVICE_TOKEN,
            ""
        )

    val seller_device_token: String?
        get() = sharedPreferences.getString(
            SELLER_DEVICE_TOKEN,
            ""
        )

    val name: String?
        get() = sharedPreferences.getString(
            NAME,
            ""
        )

    val status: String?
        get() = sharedPreferences.getString(
            STATUS,
            ""
        )

    init {
        sharedPreferences = context.getSharedPreferences(
            PREF_NAME,
            PRIVATE_MODE
        )
        editor = sharedPreferences.edit()
    }

    companion object {
        private const val PREF_NAME = "AUTH"
    }
}