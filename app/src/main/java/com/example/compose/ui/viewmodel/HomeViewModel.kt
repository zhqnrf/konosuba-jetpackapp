package com.example.compose.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose.data.CharacterRepository
import com.example.compose.model.Character
import com.example.compose.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: CharacterRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Character>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Character>>>
        get() = _uiState

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun search(newQuery: String) = viewModelScope.launch {
        _query.value = newQuery
        repository.searchCharacter(_query.value)
            .catch {
                _uiState.value = UiState.Error(it.message.toString())
            }
            .collect {
                _uiState.value = UiState.Success(it)
            }
    }

    fun updateCharacter(id: Int, newState: Boolean) = viewModelScope.launch {
        repository.updateCharacter(id, newState)
            .collect { isUpdated ->
                if (isUpdated) search(_query.value)
            }
    }
}