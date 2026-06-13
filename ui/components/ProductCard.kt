package com.example.vivitsmarket.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.vivitsmarket.R
import com.example.vivitsmarket.data.entity.ProductEntity
import java.text.NumberFormat
import java.util.Locale

@Composable
fun ProductCard(
    product: ProductEntity,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier.fillMaxWidth().clickable { onClick() }
    ) {
        Column {
            Box(modifier = Modifier.height(140.dp).fillMaxWidth()) {
                AsyncImage(model = product.foto1, contentDescription = product.judul, contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize())

                val isNew = product.kondisi.equals("Baru", ignoreCase = true)
                val badgeColor = if (isNew) Color(0xFF4CAF50) else Color(0xFF9E9E9E)
                val badgeText = if (isNew) "NEW" else "USED"

                Text(
                    text = badgeText, style = MaterialTheme.typography.labelSmall, color = Color.White, fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(8.dp).background(badgeColor, RoundedCornerShape(4.dp)).padding(horizontal = 6.dp, vertical = 2.dp).align(Alignment.TopStart)
                )

                Surface(
                    shape = RoundedCornerShape(12.dp), color = Color.Black.copy(alpha = 0.4f),
                    modifier = Modifier.align(Alignment.TopEnd).padding(8.dp)
                ) {
                    Box(modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)) {
                        Icon(Icons.Default.FavoriteBorder, contentDescription = "Wishlist", tint = Color.White, modifier = Modifier.size(16.dp))
                    }
                }
            }

            Column(modifier = Modifier.padding(12.dp)) {
                Text(text = product.judul, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, maxLines = 2, overflow = TextOverflow.Ellipsis)
                Spacer(modifier = Modifier.height(4.dp))

                val formatRupiah = NumberFormat.getNumberInstance(Locale("id", "ID")).format(product.harga)
                Text(text = "Rp $formatRupiah", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color(0xFF0055B8))

                Spacer(modifier = Modifier.height(8.dp))
                Divider(color = Color(0xFFF0F0F0))
                Spacer(modifier = Modifier.height(8.dp))

                val sellerName = when (product.sellerNrp) {
                    "5025211000", "5025231189" -> "Rachel Bronzen"
                    "5024241005" -> "Muiz Akhdan"
                    "5010251055" -> "Brenden Saputra"
                    "5009251044" -> "Siti Nurhaliza"
                    "5020241088" -> "Dimas Pratama"
                    "5004241088" -> "Gisela Putri"
                    else -> "Mahasiswa ITS"
                }

                val sellerAvatar: Any = when (product.sellerNrp) {
                    "5025211000", "5025231189" -> R.drawable.pp
                    "5024241005" -> "https://i.pravatar.cc/150?img=11"
                    "5010251055" -> "https://i.pravatar.cc/150?img=12"
                    "5009251044" -> "https://i.pravatar.cc/150?img=47"
                    "5020241088" -> "https://i.pravatar.cc/150?img=13"
                    "5004241088" -> "https://i.pravatar.cc/150?img=48"
                    else -> "https://i.pravatar.cc/150?u=${product.sellerNrp}"
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    AsyncImage(
                        model = sellerAvatar,
                        contentDescription = "Avatar Penjual",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(20.dp).clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = sellerName, style = MaterialTheme.typography.labelSmall, color = Color.DarkGray, maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
            }
        }
    }
}