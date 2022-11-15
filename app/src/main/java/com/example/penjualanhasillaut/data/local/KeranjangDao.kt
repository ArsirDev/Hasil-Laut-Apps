package com.example.penjualanhasillaut.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.penjualanhasillaut.domain.model.Keranjang
import kotlinx.coroutines.flow.Flow

@Dao
interface KeranjangDao {

    @Query("SELECT * FROM keranjang")
    fun getKeranjang(): Flow<List<Keranjang>>

    @Query("SELECT * FROM keranjang WHERE id = :id")
    suspend fun getKeranjangById(id: Int): Keranjang?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserKeranjang(keranjang: Keranjang)

    @Delete
    suspend fun deleteKeranjang(keranjang: Keranjang)
}