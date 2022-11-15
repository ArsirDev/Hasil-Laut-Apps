package com.example.penjualanhasillaut.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.penjualanhasillaut.domain.model.Keranjang

@Database(
    entities = [Keranjang::class],
    version = 1
)
abstract class KeranjangDatabase: RoomDatabase() {

    abstract val keranjangDao: KeranjangDao

    companion object {
        const val DATABASE_NAME = "db_keranjang"
    }
}