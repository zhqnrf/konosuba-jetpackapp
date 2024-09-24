package com.example.compose.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Favorite : Screen("favorite")
    object Profile : Screen("profile")
    object DetailCharacter : Screen("home/{characterId}") {
        fun createRoute(characterId: Int) = "home/$characterId"
    }
}