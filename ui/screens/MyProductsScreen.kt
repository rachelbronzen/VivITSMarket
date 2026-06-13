package com.example.vivitsmarket.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.vivitsmarket.data.entity.ProductEntity
import com.example.vivitsmarket.ui.components.MarketBottomBar
import com.example.vivitsmarket.ui.components.ProductCard
import com.example.vivitsmarket.viewmodel.MarketViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyProductsScreen(
    viewModel: MarketViewModel,
    onProductClick: (Int) -> Unit,
    onHomeClick: () -> Unit,
    onWishlistClick: () -> Unit,
    onPostClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    val allProducts by viewModel.productsState.collectAsState()
    val myProducts = allProducts.filter { it.sellerNrp == viewModel.currentUserNrp }
    var productToDelete by remember { mutableStateOf<ProductEntity?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Barang Jualanku", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            MarketBottomBar(
                currentRoute = "my_products",
                onHomeClick = onHomeClick,
                onWishlistClick = onWishlistClick,
                onPostClick = onPostClick,
                onMyProductsClick = { },
                onProfileClick = onProfileClick
            )
        }
    ) { innerPadding ->
        if (productToDelete != null) {
            AlertDialog(
                onDismissRequest = { productToDelete = null },
                title = { Text("Hapus Jualan?", fontWeight = FontWeight.Bold) },
                text = { Text("Apakah kamu yakin ingin menghapus '${productToDelete?.judul}'? Barang ini akan hilang permanen dari katalog kampus.") },
                confirmButton = {
                    Button(
                        onClick = {
                            productToDelete?.let { viewModel.deleteMyProduct(it) }
                            productToDelete = null
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F))
                    ) {
                        Text("Ya, Hapus", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                },
                dismissButton = {
                    OutlinedButton(onClick = { productToDelete = null }) {
                        Text("Batal", color = Color.DarkGray)
                    }
                },
                shape = RoundedCornerShape(16.dp),
                containerColor = Color.White
            )
        }

        if (myProducts.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(Color(0xFFF8F9FA)),
                contentAlignment = Alignment.Center
            ) {
                Text("Kamu belum memasang barang apa pun untuk dijual.", color = Color.Gray)
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF8F9FA))
                    .padding(innerPadding),
                contentPadding = PaddingValues(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 16.dp)
            ) {
                items(myProducts) { produk ->
                    Column(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                    ) {
                        ProductCard(
                            product = produk,
                            onClick = { onProductClick(produk.id) }
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = { productToDelete = produk },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFFFEBEE),
                                contentColor = Color(0xFFC62828)
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(38.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Hapus",
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Hapus Barang",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}