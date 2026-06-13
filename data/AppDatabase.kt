package com.example.vivitsmarket.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.vivitsmarket.data.dao.MarketDao
import com.example.vivitsmarket.data.entity.ProductEntity
import com.example.vivitsmarket.data.entity.UserEntity
import com.example.vivitsmarket.data.entity.WishlistEntity

@Database(
    entities = [UserEntity::class, ProductEntity::class, WishlistEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun marketDao(): MarketDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "vivits_market_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}