package com.example.vivitsmarket.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.vivitsmarket.ui.screens.*
import com.example.vivitsmarket.viewmodel.MarketViewModel

@Composable
fun VivItsMarketApp(
    viewModel: MarketViewModel,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    val navigateToBottomNav = { route: String ->
        navController.navigate(route) {
            popUpTo("home") { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }

    Scaffold { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "login",
            modifier = modifier.padding(innerPadding)
        ) {
            composable("login") {
                LoginScreen(
                    viewModel = viewModel,
                    onLoginSuccess = {
                        navController.navigate("home") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                )
            }
            composable("home") {
                HomeScreen(
                    viewModel = viewModel,
                    onProductClick = { productId -> navController.navigate("detail/$productId") },
                    onSafetyClick = { navController.navigate("safety_tips") },
                    onProfileClick = { navigateToBottomNav("profile") },
                    onPostClick = { navigateToBottomNav("post") },
                    onWishlistClick = { navigateToBottomNav("wishlist") },
                    onMyProductsClick = { navigateToBottomNav("my_products") }
                )
            }
            composable("wishlist") {
                WishlistScreen(
                    viewModel = viewModel,
                    onProductClick = { productId -> navController.navigate("detail/$productId") },
                    onHomeClick = { navigateToBottomNav("home") },
                    onPostClick = { navigateToBottomNav("post") },
                    onMyProductsClick = { navigateToBottomNav("my_products") },
                    onProfileClick = { navigateToBottomNav("profile") }
                )
            }
            composable("my_products") {
                MyProductsScreen(
                    viewModel = viewModel,
                    onProductClick = { productId -> navController.navigate("detail/$productId") },
                    onHomeClick = { navigateToBottomNav("home") },
                    onWishlistClick = { navigateToBottomNav("wishlist") },
                    onPostClick = { navigateToBottomNav("post") },
                    onProfileClick = { navigateToBottomNav("profile") }
                )
            }
            composable("post") {
                PostScreen(
                    viewModel = viewModel,
                    onPostSuccess = {
                        navController.navigate("home") {
                            popUpTo("home") { inclusive = true }
                        }
                    },
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }

            composable("profile") {
                ProfileScreen(
                    viewModel = viewModel,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }

            composable("safety_tips") {
                SafetyTipsScreen(onBackClick = { navController.popBackStack() })
            }

            composable("detail/{productId}") { backStackEntry ->
                val productId = backStackEntry.arguments?.getString("productId")?.toIntOrNull() ?: 0
                DetailScreen(
                    productId = productId,
                    viewModel = viewModel,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}