package com.example.vivitsmarket.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.vivitsmarket.R
import com.example.vivitsmarket.data.entity.ProductEntity
import com.example.vivitsmarket.ui.components.SafetyBanner
import com.example.vivitsmarket.viewmodel.MarketViewModel
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    productId: Int,
    viewModel: MarketViewModel,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val products by viewModel.productsState.collectAsState()
    val product = products.find { it.id == productId }

    if (product == null) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("Barang tidak ditemukan.") }
        return
    }

    val isWishlisted by viewModel.checkIsWishlisted(productId).collectAsState(initial = false)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail Barang", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) { Icon(Icons.Default.ArrowBack, contentDescription = "Kembali") }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            Surface(color = Color.White, shadowElevation = 16.dp, modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.padding(16.dp), horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    OutlinedButton(
                        onClick = { viewModel.toggleWishlist(product.id, isWishlisted) },
                        modifier = Modifier.weight(1f).height(50.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF0055B8)),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF0055B8))
                    ) {
                        Icon(imageVector = if (isWishlisted) Icons.Default.Favorite else Icons.Default.FavoriteBorder, contentDescription = null, tint = if (isWishlisted) Color.Red else Color(0xFF0055B8))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(if (isWishlisted) "Tersimpan" else "Simpan")
                    }

                    Button(
                        onClick = { openWhatsApp(context, product) },
                        modifier = Modifier.weight(1f).height(50.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0055B8))
                    ) { Text("Chat Penjual", fontWeight = FontWeight.Bold) }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = modifier.fillMaxSize().background(Color(0xFFF8F9FA)).padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            Box(modifier = Modifier.fillMaxWidth().background(Color.White).padding(16.dp)) {
                Column {
                    AsyncImage(
                        model = product.foto1,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxWidth().height(260.dp)
                            .clip(RoundedCornerShape(16.dp))
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        AsyncImage(
                            model = product.foto2,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.weight(1f).height(90.dp)
                                .clip(RoundedCornerShape(12.dp))
                        )
                        AsyncImage(
                            model = product.foto3,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.weight(1f).height(90.dp)
                                .clip(RoundedCornerShape(12.dp))
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Column(modifier = Modifier.fillMaxWidth().background(Color.White).padding(16.dp)) {

                // --- PERBAIKAN FORMAT HARGA DI DETAIL ---
                val formatRupiah =
                    NumberFormat.getNumberInstance(Locale("id", "ID")).format(product.harga)
                Text(
                    text = "Rp $formatRupiah",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0055B8)
                )

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = product.judul,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Surface(
                        color = Color(0xFFE3F2FD),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = product.kategori,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            color = Color(0xFF0055B8),
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Surface(
                        color = if (product.kondisi == "Baru") Color(0xFFE8F5E9) else Color(
                            0xFFFFF3E0
                        ), shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = product.kondisi,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            color = if (product.kondisi == "Baru") Color(0xFF2E7D32) else Color(
                                0xFFE65100
                            ),
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

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

            val sellerInfo = when (product.sellerNrp) {
                "5025211000", "5025231189" -> "Teknik Informatika • Angkatan 2021"
                "5024241005" -> "Sistem Informasi • Angkatan 2024"
                "5010251055" -> "Teknik Kimia • Angkatan 2025"
                "5009251044" -> "Teknik Elektro • Angkatan 2025"
                "5020241088" -> "Desain Interior • Angkatan 2024"
                "5004241088" -> "Biologi • Angkatan 2024"
                else -> "NRP: ${product.sellerNrp}"
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

            Column(modifier = Modifier.fillMaxWidth().background(Color.White).padding(16.dp)) {
                Text(
                    text = "Informasi Penjual",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .border(1.dp, Color(0xFFEEEEEE), RoundedCornerShape(12.dp)).padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = sellerAvatar,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(50.dp).clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = sellerName,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = sellerInfo,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                    Icon(
                        Icons.Rounded.CheckCircle,
                        contentDescription = "Verified",
                        tint = Color(0xFF0055B8),
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Column(modifier = Modifier.fillMaxWidth().background(Color.White).padding(16.dp)) {
                Text(
                    text = "Deskripsi Barang",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = product.deskripsi,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.DarkGray,
                    lineHeight = MaterialTheme.typography.bodyMedium.lineHeight * 1.4f
                )
                Spacer(modifier = Modifier.height(24.dp))
                SafetyBanner(onLearnMoreClick = {
                    Toast.makeText(
                        context,
                        "Silakan lihat tips lengkap di halaman utama",
                        Toast.LENGTH_SHORT
                    ).show()
                })
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

fun openWhatsApp(context: Context, product: ProductEntity) {
    val message = "Halo, saya lihat barang '${product.judul}' seharga Rp ${product.harga} di VivITS Market. Apakah bisa janjian COD di area kampus ITS?"
    val intent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse("https://api.whatsapp.com/send?phone=${product.sellerNrp}&text=${Uri.encode(message)}")
    }
    try {
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "Aplikasi WhatsApp tidak ditemukan", Toast.LENGTH_SHORT).show()
    }
}