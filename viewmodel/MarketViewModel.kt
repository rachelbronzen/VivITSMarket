package com.example.vivitsmarket.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.vivitsmarket.data.AppDatabase
import com.example.vivitsmarket.data.entity.ProductEntity
import com.example.vivitsmarket.repository.MarketRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed interface PostUiState {
    object Idle : PostUiState
    object Loading : PostUiState
    object Success : PostUiState
    data class Error(val message: String) : PostUiState
}

class MarketViewModel(
    application: Application,
    private val repository: MarketRepository
) : AndroidViewModel(application) {

    val currentUserNrp = "5025231189"

    private val _selectedKondisi = MutableStateFlow("Semua")
    val selectedKondisi: StateFlow<String> = _selectedKondisi

    private val _selectedKategori = MutableStateFlow("Semua")
    val selectedKategori: StateFlow<String> = _selectedKategori

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    val productsState: StateFlow<List<ProductEntity>> = combine(
        repository.getAllProducts(),
        _selectedKondisi,
        _selectedKategori,
        _searchQuery
    ) { allProducts, kondisi, kategori, query ->
        allProducts.filter { product ->
            val matchKondisi = kondisi == "Semua" || product.kondisi == kondisi
            val matchKategori = kategori == "Semua" || product.kategori == kategori
            val matchSearch = product.judul.contains(query, ignoreCase = true)
            matchKondisi && matchKategori && matchSearch
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val wishlistState: StateFlow<List<ProductEntity>> = repository.getWishlistProducts(currentUserNrp)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _postState = MutableStateFlow<PostUiState>(PostUiState.Idle)
    val postState: StateFlow<PostUiState> = _postState
    init {
        seedDummyDataIfNeeded()
    }
    private fun seedDummyDataIfNeeded() {
        viewModelScope.launch {
            val currentProducts = repository.getAllProducts().first()
            if (currentProducts.isEmpty()) {
                val dummyProducts = listOf(

                    ProductEntity(
                        sellerNrp = "5024241005", judul = "Buku Kalkulus 1",
                        deskripsi = "Kondisi masih bagus, ada coretan stabilo sedikit untuk highlight rumus penting. Sangat cocok untuk Maba yang butuh buku murah.",
                        harga = 75000, kondisi = "Bekas", kategori = "Buku",
                        foto1 = "https://images.unsplash.com/photo-1589829085413-56de8ae18c73?w=500&q=80",
                        foto2 = "https://images.unsplash.com/photo-1589829085413-56de8ae18c73?w=500&q=80",
                        foto3 = "https://images.unsplash.com/photo-1589829085413-56de8ae18c73?w=500&q=80"
                    ),

                    ProductEntity(
                        sellerNrp = "5025231189", judul = "Monitor LG 24 Inch IPS",
                        deskripsi = "Dijual cepat, monitor kesayangan yang sering dipakai buat ngoding PySpark dan bikin dashboard Streamlit. Mulus, no dead pixel, lengkap dus.",
                        harga = 1200000, kondisi = "Bekas", kategori = "Elektronik",
                        foto1 = "https://images.unsplash.com/photo-1527443224154-c4a3942d3acf?auto=format&fit=crop&w=500",
                        foto2 = "https://images.unsplash.com/photo-1586952518485-11b180e92764?auto=format&fit=crop&w=500",
                        foto3 = "https://images.unsplash.com/photo-1616056475685-6f103b41d0cc?auto=format&fit=crop&w=500"
                    ),
                    ProductEntity(
                        sellerNrp = "5010251055", judul = "Jas Laboratorium Lengan Panjang (Size L)",
                        deskripsi = "Salah beli ukuran, belum pernah dipakai sama sekali ke lab. Masih wangi toko.",
                        harga = 85000, kondisi = "Baru", kategori = "Alat Praktikum",
                        foto1 = "https://images.unsplash.com/photo-1629909613654-28e377c37b09?auto=format&fit=crop&w=500",
                        foto2 = "https://images.unsplash.com/photo-1584982751601-97dcc096659c?auto=format&fit=crop&w=500",
                        foto3 = "https://images.unsplash.com/photo-1581093458791-9f3c3900df4b?auto=format&fit=crop&w=500"
                    ),

                    ProductEntity(
                        sellerNrp = "5009251044", judul = "Raket Badminton Yonex + Tas",
                        deskripsi = "Jarang dipakai karena sibuk tugas akhir. Senar tarikan 28 lbs, grip masih baru.",
                        harga = 250000, kondisi = "Bekas", kategori = "Lainnya",
                        foto1 = "https://images.unsplash.com/photo-1626224583764-f87db24ac4ea?w=500&q=80",
                        foto2 = "https://images.unsplash.com/photo-1626224583764-f87db24ac4ea?w=500&q=80",
                        foto3 = "https://images.unsplash.com/photo-1626224583764-f87db24ac4ea?w=500&q=80"
                    ),

                    ProductEntity(
                        sellerNrp = "5020241088", judul = "Kipas Angin Miyako Berdiri",
                        deskripsi = "Angin masih kencang banget. Cocok buat nahan hawa panas kosan daerah Keputih.",
                        harga = 120000, kondisi = "Bekas", kategori = "Perabotan Kos",
                        foto1 = "https://images.unsplash.com/photo-1599839619722-39751411ea63?w=500&q=80",
                        foto2 = "https://images.unsplash.com/photo-1599839619722-39751411ea63?w=500&q=80",
                        foto3 = "https://images.unsplash.com/photo-1599839619722-39751411ea63?w=500&q=80"
                    ),

                    ProductEntity(
                        sellerNrp = "5025231189", judul = "NVIDIA Jetson Nano Developer Kit",
                        deskripsi = "Bekas dipakai untuk training model Computer Vision (YOLO) pendeteksi tanaman invasif. Kondisi normal dan siap pakai.",
                        harga = 1800000, kondisi = "Bekas", kategori = "Elektronik",
                        foto1 = "https://images.unsplash.com/photo-1591488320449-011701bb6704?auto=format&fit=crop&w=500",
                        foto2 = "https://images.unsplash.com/photo-1587202372634-32705e3bf49c?auto=format&fit=crop&w=500",
                        foto3 = "https://images.unsplash.com/photo-1626218174358-7769486c4b79?auto=format&fit=crop&w=500"
                    )
                )

                dummyProducts.forEach { repository.insertProduct(it) }
            }
        }
    }
    fun setFilterKategori(kategori: String) {
        if (_selectedKategori.value == kategori) {
            _selectedKategori.value = "Semua"
        } else {
            _selectedKategori.value = kategori
        }
    }
    fun setFilterKondisi(kondisi: String) {
        if (_selectedKondisi.value == kondisi) {
            _selectedKondisi.value = "Semua"
        } else {
            _selectedKondisi.value = kondisi
        }
    }

    fun onSearchQueryChange(newQuery: String) { _searchQuery.value = newQuery }
    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    fun setLoggedIn() {
        _isLoggedIn.value = true
    }
    private val _profilePicUri = MutableStateFlow<String?>(null)
    val profilePicUri: StateFlow<String?> = _profilePicUri
    fun updateProfilePic(newUri: String) {
        _profilePicUri.value = newUri
    }
    fun postProduct(judul: String, deskripsi: String, harga: Long, kondisi: String, kategori: String, foto1: String, foto2: String, foto3: String) {
        viewModelScope.launch {
            _postState.value = PostUiState.Loading
            if (foto1.isBlank() || foto2.isBlank() || foto3.isBlank()) {
                _postState.value = PostUiState.Error("Gagal! Anda wajib mengunggah minimal 3 foto asli dari berbagai arah.")
                return@launch
            }
            val activeCount = repository.getActiveProductCount(currentUserNrp)
            if (activeCount >= 5) {
                _postState.value = PostUiState.Error("Batas limit tercapai! Setiap mahasiswa hanya boleh memiliki maksimal 5 barang aktif dijual.")
                return@launch
            }
            val newProduct = ProductEntity(
                sellerNrp = currentUserNrp, judul = judul, deskripsi = deskripsi, harga = harga,
                kondisi = kondisi, kategori = kategori, foto1 = foto1, foto2 = foto2, foto3 = foto3
            )
            repository.insertProduct(newProduct)
            _postState.value = PostUiState.Success
        }
    }

    fun resetPostState() { _postState.value = PostUiState.Idle }

    fun toggleWishlist(productId: Int, isCurrentlyWishlisted: Boolean) {
        viewModelScope.launch {
            if (isCurrentlyWishlisted) repository.removeFromWishlist(currentUserNrp, productId)
            else repository.addToWishlist(currentUserNrp, productId)
        }
    }

    fun checkIsWishlisted(productId: Int): Flow<Boolean> = repository.isWishlisted(currentUserNrp, productId)
    fun deleteMyProduct(product: ProductEntity) = viewModelScope.launch { repository.deleteProduct(product) }

    class Factory(private val application: Application) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MarketViewModel::class.java)) {
                val database = AppDatabase.getDatabase(application)
                val repository = MarketRepository(database.marketDao())
                return MarketViewModel(application, repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}