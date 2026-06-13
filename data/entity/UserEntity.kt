package com.example.vivitsmarket.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val nrp: String, // NRP digunakan sebagai ID unik utama mahasiswa ITS
    val namaLengkap: String,
    val nomorWhatsapp: String,
    val fakultas: String
)