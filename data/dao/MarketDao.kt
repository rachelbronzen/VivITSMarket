package com.example.vivitsmarket.data.dao

import androidx.room.*
import com.example.vivitsmarket.data.entity.ProductEntity
import com.example.vivitsmarket.data.entity.WishlistEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MarketDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: ProductEntity)

    @Delete
    suspend fun deleteProduct(product: ProductEntity)

    @Query("SELECT * FROM products WHERE kondisi = :kondisi AND kategori = :kategori ORDER BY timestamp DESC")
    fun getProductsByDoubleFilter(kondisi: String, kategori: String): Flow<List<ProductEntity>>

    @Query("SELECT * FROM products ORDER BY timestamp DESC")
    fun getAllProducts(): Flow<List<ProductEntity>>

    @Query("SELECT COUNT(*) FROM products WHERE sellerNrp = :nrp")
    suspend fun getActiveProductCountByNrp(nrp: String): Int


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addToWishlist(wishlist: WishlistEntity)

    @Query("DELETE FROM wishlist WHERE userNrp = :userNrp AND productId = :productId")
    suspend fun removeFromWishlist(userNrp: String, productId: Int)

    @Query("SELECT EXISTS(SELECT 1 FROM wishlist WHERE userNrp = :userNrp AND productId = :productId)")
    fun isWishlisted(userNrp: String, productId: Int): Flow<Boolean>

    @Query("""
        SELECT p.* FROM products p 
        INNER JOIN wishlist w ON p.id = w.productId 
        WHERE w.userNrp = :userNrp ORDER BY p.timestamp DESC
    """)
    fun getWishlistProducts(userNrp: String): Flow<List<ProductEntity>>
}