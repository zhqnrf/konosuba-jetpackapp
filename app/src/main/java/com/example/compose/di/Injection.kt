package com.example.compose.di

import com.example.compose.data.CharacterRepository

object Injection {
    fun provideRepository(): CharacterRepository {
        return CharacterRepository.getInstance()
    }
}