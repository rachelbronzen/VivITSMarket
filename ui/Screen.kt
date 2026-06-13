package com.example.vivitsmarket.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector? = null) {
    object Home : Screen("home", "Katalog", Icons.Default.Home)
    object Post : Screen("post", "Jual", Icons.Default.AddCircle)
    object Wishlist : Screen("wishlist", "Wishlist", Icons.Default.Favorite)
    object Detail : Screen("detail/{productId}", "Detail") {
        fun createRoute(productId: Int) = "detail/$productId"
    }
}