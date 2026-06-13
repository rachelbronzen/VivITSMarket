package com.example.vivitsmarket.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.vivitsmarket.ui.components.CategoryMenu
import com.example.vivitsmarket.ui.components.HomeHeader
import com.example.vivitsmarket.ui.components.ProductCard
import com.example.vivitsmarket.ui.components.SafetyBanner
import com.example.vivitsmarket.ui.components.MarketBottomBar
import com.example.vivitsmarket.viewmodel.MarketViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: MarketViewModel,
    onProductClick: (Int) -> Unit,
    onSafetyClick: () -> Unit,
    onProfileClick: () -> Unit,
    onPostClick: () -> Unit,
    onWishlistClick: () -> Unit,
    onMyProductsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val products by viewModel.productsState.collectAsStateWithLifecycle()
    val selectedKondisi by viewModel.selectedKondisi.collectAsStateWithLifecycle()

    val selectedKategori by viewModel.selectedKategori.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()

    val daftarKondisi = listOf("Semua", "Baru", "Bekas")

    val namaPanggilan = when (viewModel.currentUserNrp) {
        "5025211000", "5025231189" -> "Rachel Bronzen"
        "5024241005" -> "Muiz Akhdan"
        "5010251055" -> "Brenden Saputra"
        "5009251044" -> "Siti Nurhaliza"
        "5020241088" -> "Dimas Pratama"
        "5004241088" -> "Gisela Putri"
        else -> "Mahasiswa ITS"
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            MarketBottomBar(
                currentRoute = "home",
                onHomeClick = { },
                onWishlistClick = onWishlistClick,
                onPostClick = onPostClick,
                onMyProductsClick = onMyProductsClick,
                onProfileClick = onProfileClick
            )
        }
    ) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize().background(Color(0xFFF8F9FA)).padding(innerPadding),
        ) {
            item(span = { GridItemSpan(2) }) {
                HomeHeader(
                    userName = namaPanggilan,
                    searchQuery = searchQuery,
                    onSearchChange = { viewModel.onSearchQueryChange(it) },
                    onProfileClick = onProfileClick
                )
            }

            item(span = { GridItemSpan(2) }) {
                Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) { SafetyBanner(onLearnMoreClick = onSafetyClick) }
            }

            item(span = { GridItemSpan(2) }) {
                CategoryMenu(
                    selectedCategory = selectedKategori,
                    onCategoryClick = { kategoriBaru ->
                        viewModel.setFilterKategori(kategoriBaru)
                    }
                )
            }

            item(span = { GridItemSpan(2) }) {
                Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    daftarKondisi.forEach { kondisi ->
                        FilterChip(
                            selected = selectedKondisi == kondisi, onClick = { viewModel.setFilterKondisi(kondisi) },
                            label = { Text(kondisi, fontWeight = FontWeight.Bold) },
                            colors = FilterChipDefaults.filterChipColors(selectedContainerColor = Color(0xFF0055B8), selectedLabelColor = Color.White, containerColor = Color.White),
                            shape = RoundedCornerShape(20.dp), border = FilterChipDefaults.filterChipBorder(enabled = true, selected = selectedKondisi == kondisi, borderColor = Color.LightGray, selectedBorderColor = Color.Transparent)
                        )
                    }
                }
            }

            item(span = { GridItemSpan(2) }) {
                Text("✨ Baru Ditambahkan", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 12.dp))
            }

            if (products.isEmpty()) {
                item(span = { GridItemSpan(2) }) {
                    Box(modifier = Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) { Text("Tidak ada barang.", color = Color.Gray) }
                }
            } else {
                items(products) { produk ->
                    Box(modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 16.dp)) { ProductCard(product = produk, onClick = { onProductClick(produk.id) }) }
                }
            }
        }
    }
}