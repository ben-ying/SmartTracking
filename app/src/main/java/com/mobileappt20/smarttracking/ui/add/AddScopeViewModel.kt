package com.mobileappt20.smarttracking.ui.add

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class ScopeUiState(
    val scopeIndex: Int = 0,
)

class AddScopeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ScopeUiState())
    val uiState: StateFlow<ScopeUiState> = _uiState.asStateFlow()

    fun setScopeIndex(index: Int, delta: Int) {
        var scopeIndex = index + delta
        if (scopeIndex > 3) {
            scopeIndex = 0
        } else if (scopeIndex < 0) {
            scopeIndex = 3
        }

        _uiState.update { currentState ->
            currentState.copy(
                scopeIndex = scopeIndex
            )
        }
    }
}