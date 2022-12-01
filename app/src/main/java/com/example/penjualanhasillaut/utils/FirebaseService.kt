package com.example.penjualanhasillaut.utils

import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging

object FirebaseService {

    private const val tag = "FirebaseService"

    fun fireBaseToken() = FirebaseMessaging.getInstance().token
    fun deleteFirebaseToken() = runCatching {
        FirebaseMessaging.getInstance().deleteToken().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.e(tag, "deleteFirebaseToken: ${task.result}")
            }
        }
    }
}