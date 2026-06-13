package com.example.vivitsmarket.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wishlist")
data class WishlistEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userNrp: String, // Siapa mahasiswa yang nge-save
    val productId: Int   // Barang apa yang di-save
)