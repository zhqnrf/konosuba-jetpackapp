package com.example.compose.model

data class Character(
    val id: Int,
    val name: String,
    val image: Int,
    val description: String,
    val positional: String,
    val rating: Double,
    val active: String,
    val origin: String,
    val gender: String,
    var isFavorite: Boolean = false
)
