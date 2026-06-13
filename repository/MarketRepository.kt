package com.example.vivitsmarket.repository

import com.example.vivitsmarket.data.dao.MarketDao
import com.example.vivitsmarket.data.entity.ProductEntity
import com.example.vivitsmarket.data.entity.WishlistEntity
import kotlinx.coroutines.flow.Flow

class MarketRepository(private val marketDao: MarketDao) {

    fun getAllProducts(): Flow<List<ProductEntity>> = marketDao.getAllProducts()

    fun getProductsByDoubleFilter(kondisi: String, kategori: String): Flow<List<ProductEntity>> {
        return marketDao.getProductsByDoubleFilter(kondisi, kategori)
    }

    suspend fun insertProduct(product: ProductEntity) = marketDao.insertProduct(product)

    suspend fun deleteProduct(product: ProductEntity) = marketDao.deleteProduct(product)

    suspend fun getActiveProductCount(nrp: String): Int = marketDao.getActiveProductCountByNrp(nrp)

    suspend fun addToWishlist(userNrp: String, productId: Int) {
        marketDao.addToWishlist(WishlistEntity(userNrp = userNrp, productId = productId))
    }

    suspend fun removeFromWishlist(userNrp: String, productId: Int) {
        marketDao.removeFromWishlist(userNrp, productId)
    }

    fun isWishlisted(userNrp: String, productId: Int): Flow<Boolean> = marketDao.isWishlisted(userNrp, productId)

    fun getWishlistProducts(userNrp: String): Flow<List<ProductEntity>> = marketDao.getWishlistProducts(userNrp)
}