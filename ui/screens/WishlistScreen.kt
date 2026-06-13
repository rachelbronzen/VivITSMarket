package com.example.vivitsmarket.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.vivitsmarket.ui.components.MarketBottomBar
import com.example.vivitsmarket.ui.components.ProductCard
import com.example.vivitsmarket.viewmodel.MarketViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WishlistScreen(
    viewModel: MarketViewModel,
    onProductClick: (Int) -> Unit,
    onHomeClick: () -> Unit,
    onPostClick: () -> Unit,
    onMyProductsClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    val wishlist by viewModel.wishlistState.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Barang Incaran (Wishlist)", fontWeight = FontWeight.Bold) }, colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)) },
        bottomBar = {
            MarketBottomBar(
                currentRoute = "wishlist",
                onHomeClick = onHomeClick,
                onWishlistClick = { },
                onPostClick = onPostClick,
                onMyProductsClick = onMyProductsClick,
                onProfileClick = onProfileClick
            )
        }
    ) { innerPadding ->
        if (wishlist.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(innerPadding).background(Color(0xFFF8F9FA)), contentAlignment = Alignment.Center) {
                Text("Wishlist kamu masih kosong nih.", color = Color.Gray)
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize().background(Color(0xFFF8F9FA)).padding(innerPadding),
                contentPadding = PaddingValues(top = 16.dp)
            ) {
                items(wishlist) { produk ->
                    Box(modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 16.dp)) {
                        ProductCard(product = produk, onClick = { onProductClick(produk.id) })
                    }
                }
            }
        }
    }
}