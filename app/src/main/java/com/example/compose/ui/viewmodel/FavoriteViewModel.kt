package com.example.compose.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose.data.CharacterRepository
import com.example.compose.model.Character
import com.example.compose.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val repository: CharacterRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Character>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Character>>>
        get() = _uiState

    fun getFavoriteCharacter() = viewModelScope.launch {
        repository.getFavoriteCharacter()
            .catch {
                _uiState.value = UiState.Error(it.message.toString())
            }
            .collect {
                _uiState.value = UiState.Success(it)
            }
    }

    fun updateCharacter(id: Int, newState: Boolean) {
        repository.updateCharacter(id, newState)
        getFavoriteCharacter()
    }
}