package com.example.vivitsmarket.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val sellerNrp: String, // Menghubungkan barang dengan NRP penjual
    val judul: String,
    val deskripsi: String,
    val harga: Long,
    val kondisi: String, // "Baru" atau "Bekas" (Filter Utama)
    val kategori: String, // "Elektronik", "Praktikum", "Buku", "Perabotan", dll (Filter Kedua)
    val foto1: String, // Path gambar/URL wajib 1
    val foto2: String, // Path gambar/URL wajib 2
    val foto3: String, // Path gambar/URL wajib 3
    val timestamp: Long = System.currentTimeMillis() // Untuk urutan barang terbaru
)