package com.example.vivitsmarket.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SafetyBanner(
    onLearnMoreClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val warningBg = Color(0xFFFFF9E6)
    val warningIconColor = Color(0xFFFFB300)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(warningBg, RoundedCornerShape(12.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = "Warning",
            tint = warningIconColor,
            modifier = Modifier.size(32.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Transaksi Aman di VivITS Market",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = "Lakukan COD di area kampus yang ramai dan jangan transfer sebelum cek barang!",
                style = MaterialTheme.typography.bodySmall,
                color = Color.DarkGray,
                lineHeight = MaterialTheme.typography.bodySmall.lineHeight * 1.2f
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Button(
            onClick = onLearnMoreClick,
            colors = ButtonDefaults.buttonColors(containerColor = warningIconColor),
            shape = RoundedCornerShape(20.dp),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
            modifier = Modifier.height(32.dp)
        ) {
            Text("Pelajari Tips", style = MaterialTheme.typography.labelSmall, color = Color.White)
        }
    }
}