package com.example.vivitsmarket.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
data class CategoryItem(val title: String, val filterName: String, val iconUrl: String)

@Composable
fun CategoryMenu(
    selectedCategory: String,
    onCategoryClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val categories = listOf(
        CategoryItem("Lainnya", "Lainnya", "https://cdn-icons-png.flaticon.com/512/8364/8364053.png"),
        CategoryItem("Buku", "Buku", "https://cdn-icons-png.flaticon.com/512/3145/3145765.png"),
        CategoryItem("Elektronik", "Elektronik", "https://cdn-icons-png.flaticon.com/512/3659/3659899.png"),
        CategoryItem("Alat\nPraktikum", "Alat Praktikum", "https://cdn-icons-png.flaticon.com/512/3022/3022204.png"),
        CategoryItem("Otomotif", "Otomotif", "https://cdn-icons-png.flaticon.com/512/741/741407.png"),
        CategoryItem("Perabotan\nKos", "Perabotan Kos", "https://cdn-icons-png.flaticon.com/512/2611/2611026.png"),
        CategoryItem("Pakaian", "Pakaian", "https://cdn-icons-png.flaticon.com/512/3345/3345373.png")
    )

    LazyRow(
        modifier = modifier.fillMaxWidth().padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(categories) { category ->
            val isSelected = selectedCategory == category.filterName
            val bgColor = if (isSelected) Color(0xFF0055B8).copy(alpha = 0.15f) else Color(0xFFF0F4F8)
            val borderColor = if (isSelected) Color(0xFF0055B8) else Color.Transparent

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .width(70.dp)
                    .clickable { onCategoryClick(category.filterName) }
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(bgColor)
                        .border(width = if (isSelected) 2.dp else 0.dp, color = borderColor, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(model = category.iconUrl, contentDescription = category.title, modifier = Modifier.size(32.dp))
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = category.title,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                    color = if (isSelected) Color(0xFF0055B8) else Color.Black,
                    textAlign = TextAlign.Center,
                    lineHeight = MaterialTheme.typography.labelSmall.lineHeight * 1.2f
                )
            }
        }
    }
}