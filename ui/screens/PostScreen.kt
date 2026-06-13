package com.example.vivitsmarket.ui.screens

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.vivitsmarket.viewmodel.MarketViewModel
import com.example.vivitsmarket.viewmodel.PostUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostScreen(
    viewModel: MarketViewModel,
    onPostSuccess: () -> Unit,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val postState by viewModel.postState.collectAsState()

    var judul by remember { mutableStateOf("") }
    var deskripsi by remember { mutableStateOf("") }
    var harga by remember { mutableStateOf("") }
    var kondisi by remember { mutableStateOf("Baru") }
    var kategori by remember { mutableStateOf("Buku Referensi") }
    var imageUri1 by remember { mutableStateOf<Uri?>(null) }
    var imageUri2 by remember { mutableStateOf<Uri?>(null) }
    var imageUri3 by remember { mutableStateOf<Uri?>(null) }

    val contentResolver = context.contentResolver
    val launcher1 = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            try { contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION) } catch (e: Exception) { e.printStackTrace() }
            imageUri1 = uri
        }
    }
    val launcher2 = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            try { contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION) } catch (e: Exception) { e.printStackTrace() }
            imageUri2 = uri
        }
    }

    val launcher3 = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            try { contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION) } catch (e: Exception) { e.printStackTrace() }
            imageUri3 = uri
        }
    }

    val daftarKategori = listOf(
        "Buku ", "Elektronik", "Alat Praktikum",
        "Otomotif",
        "Perabotan Kos", "Pakaian", "Lainnya"
    )

    LaunchedEffect(postState) {
        when (postState) {
            is PostUiState.Success -> {
                Toast.makeText(context, "Barang berhasil dipasang!", Toast.LENGTH_SHORT).show()
                viewModel.resetPostState()
                onPostSuccess()
            }
            is PostUiState.Error -> {
                Toast.makeText(context, (postState as PostUiState.Error).message, Toast.LENGTH_LONG).show()
                viewModel.resetPostState()
            }
            else -> {}
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Jual Barang", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) { Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Kembali") }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            Box(modifier = Modifier.fillMaxWidth().background(Color.White).padding(16.dp)) {
                Button(
                    onClick = {
                        viewModel.postProduct(
                            judul = judul, deskripsi = deskripsi, harga = harga.toLongOrNull() ?: 0L,
                            kondisi = kondisi, kategori = kategori,
                            foto1 = imageUri1?.toString() ?: "",
                            foto2 = imageUri2?.toString() ?: "",
                            foto3 = imageUri3?.toString() ?: ""
                        )
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0055B8)),
                    enabled = judul.isNotBlank() && harga.isNotBlank() && deskripsi.isNotBlank() &&
                            imageUri1 != null && imageUri2 != null && imageUri3 != null
                ) {
                    if (postState is PostUiState.Loading) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                    } else {
                        Text("Pasang Iklan Sekarang", fontSize = MaterialTheme.typography.titleMedium.fontSize, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().background(Color(0xFFF8F9FA)).padding(innerPadding)
                .verticalScroll(rememberScrollState()).padding(16.dp)
        ) {
            Text("Foto Barang (Wajib 3)", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Box(
                    modifier = Modifier.weight(1f).aspectRatio(1f).background(Color.White, RoundedCornerShape(12.dp))
                        .border(1.dp, Color.LightGray, RoundedCornerShape(12.dp))
                        .clickable { launcher1.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) },
                    contentAlignment = Alignment.Center
                ) {
                    if (imageUri1 != null) {
                        AsyncImage(model = imageUri1, contentDescription = null, contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(12.dp)))
                    } else {
                        Icon(Icons.Default.Add, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(32.dp))
                    }
                }
                Box(
                    modifier = Modifier.weight(1f).aspectRatio(1f).background(Color.White, RoundedCornerShape(12.dp))
                        .border(1.dp, Color.LightGray, RoundedCornerShape(12.dp))
                        .clickable { launcher2.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) },
                    contentAlignment = Alignment.Center
                ) {
                    if (imageUri2 != null) {
                        AsyncImage(model = imageUri2, contentDescription = null, contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(12.dp)))
                    } else {
                        Icon(Icons.Default.Add, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(32.dp))
                    }
                }

                Box(
                    modifier = Modifier.weight(1f).aspectRatio(1f).background(Color.White, RoundedCornerShape(12.dp))
                        .border(1.dp, Color.LightGray, RoundedCornerShape(12.dp))
                        .clickable { launcher3.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) },
                    contentAlignment = Alignment.Center
                ) {
                    if (imageUri3 != null) {
                        AsyncImage(model = imageUri3, contentDescription = null, contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(12.dp)))
                    } else {
                        Icon(Icons.Default.Add, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(32.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text("Detail Barang", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = judul, onValueChange = { judul = it }, label = { Text("Nama Barang") },
                modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = Color.White, unfocusedContainerColor = Color.White)
            )
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = harga, onValueChange = { harga = it.filter { char -> char.isDigit() } },
                label = { Text("Harga (Rp)") }, keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = Color.White, unfocusedContainerColor = Color.White)
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text("Kategori", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold, color = Color.DarkGray)
            LazyRow(modifier = Modifier.fillMaxWidth().padding(top = 8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(daftarKategori) { kat ->
                    FilterChip(
                        selected = kategori == kat, onClick = { kategori = kat }, label = { Text(kat) },
                        colors = FilterChipDefaults.filterChipColors(selectedContainerColor = Color(0xFF0055B8), selectedLabelColor = Color.White),
                        shape = RoundedCornerShape(20.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            Text("Kondisi", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold, color = Color.DarkGray)
            Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("Baru", "Bekas").forEach { opsi ->
                    FilterChip(
                        selected = kondisi == opsi, onClick = { kondisi = opsi }, label = { Text(opsi) },
                        colors = FilterChipDefaults.filterChipColors(selectedContainerColor = Color(0xFF0055B8), selectedLabelColor = Color.White),
                        shape = RoundedCornerShape(20.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = deskripsi, onValueChange = { deskripsi = it }, label = { Text("Deskripsi Lengkap") },
                modifier = Modifier.fillMaxWidth().height(120.dp), shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = Color.White, unfocusedContainerColor = Color.White)
            )
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}