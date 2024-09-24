package com.example.compose.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose.data.CharacterRepository
import com.example.compose.model.Character
import com.example.compose.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: CharacterRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<Character>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Character>>
        get() = _uiState

    fun getCharacterById(id: Int) = viewModelScope.launch {
        _uiState.value = UiState.Loading
        _uiState.value = UiState.Success(repository.getCharacterById(id))
    }


    fun updateCharacter(id: Int, newState: Boolean) = viewModelScope.launch {
        repository.updateCharacter(id, !newState)
            .collect { isUpdated ->
                if (isUpdated) getCharacterById(id)
            }
    }

}