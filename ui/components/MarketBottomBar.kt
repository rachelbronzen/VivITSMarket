package com.example.vivitsmarket.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun MarketBottomBar(
    currentRoute: String,
    onHomeClick: () -> Unit,
    onWishlistClick: () -> Unit,
    onPostClick: () -> Unit,
    onMyProductsClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    BottomAppBar(
        containerColor = Color.White,
        tonalElevation = 8.dp,
        contentPadding = PaddingValues(0.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavIcon(Icons.Filled.Home, "Home", currentRoute == "home", onHomeClick)
            BottomNavIcon(Icons.Outlined.FavoriteBorder, "Wishlist", currentRoute == "wishlist", onWishlistClick)

            FloatingActionButton(
                onClick = onPostClick,
                shape = CircleShape,
                containerColor = Color(0xFF0055B8),
                contentColor = Color.White,
                elevation = FloatingActionButtonDefaults.elevation(0.dp),
                modifier = Modifier.size(50.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Jual", modifier = Modifier.size(28.dp))
            }

            BottomNavIcon(Icons.Outlined.List, "My Products", currentRoute == "my_products", onMyProductsClick)
            BottomNavIcon(Icons.Outlined.Person, "Profile", currentRoute == "profile", onProfileClick)
        }
    }
}

@Composable
fun BottomNavIcon(icon: ImageVector, label: String, selected: Boolean, onClick: () -> Unit) {
    val color = if (selected) Color(0xFF0055B8) else Color.Gray
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onClick() }
            .padding(8.dp)
    ) {
        Icon(imageVector = icon, contentDescription = label, tint = color, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = label, style = MaterialTheme.typography.labelSmall, color = color)
    }
}