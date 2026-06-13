package com.example.vivitsmarket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vivitsmarket.ui.VivItsMarketApp
import com.example.vivitsmarket.ui.theme.VivITSMarketTheme
import com.example.vivitsmarket.viewmodel.MarketViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            VivITSMarketTheme {
                val marketViewModel: MarketViewModel = viewModel(
                    factory = MarketViewModel.Factory(application)
                )

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    VivItsMarketApp(viewModel = marketViewModel)
                }
            }
        }
    }
}