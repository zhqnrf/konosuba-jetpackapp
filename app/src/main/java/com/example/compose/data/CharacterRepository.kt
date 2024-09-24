package com.example.compose.data

import com.example.compose.model.CharacterData
import com.example.compose.model.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class CharacterRepository {
    private val KonosubaChar = mutableListOf<Character>()

    init {
        if (KonosubaChar.isEmpty()) {
            CharacterData.konosubaCharacters.forEach {
                KonosubaChar.add(it)
            }
        }
    }

    fun getCharacterById(characterId: Int): Character {
        return KonosubaChar.first { it.id == characterId }
    }

    fun getFavoriteCharacter(): Flow<List<Character>> {
        return flowOf(KonosubaChar.filter { it.isFavorite })
    }

    fun searchCharacter(query: String) = flow {
        val data = KonosubaChar.filter {
            it.name.contains(query, ignoreCase = true)
        }
        emit(data)
    }

    fun getCharactersByOrigin(origin: String): Flow<List<Character>> {
        return flow {
            val data = KonosubaChar.filter { it.origin.equals(origin, ignoreCase = true) }
            emit(data)
        }
    }

    fun getCharactersByGender(gender: String): Flow<List<Character>> {
        return flow {
            val data = KonosubaChar.filter { it.gender.equals(gender, ignoreCase = true) }
            emit(data)
        }
    }

    fun updateCharacter(characterId: Int, newState: Boolean): Flow<Boolean> {
        val index = KonosubaChar.indexOfFirst { it.id == characterId }
        val result = if (index >= 0) {
            val character = KonosubaChar[index]
            KonosubaChar[index] = character.copy(isFavorite = newState)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    companion object {
        @Volatile
        private var instance: CharacterRepository? = null

        fun getInstance(): CharacterRepository =
            instance ?: synchronized(this) {
                CharacterRepository().apply {
                    instance = this
                }
            }
    }
}
