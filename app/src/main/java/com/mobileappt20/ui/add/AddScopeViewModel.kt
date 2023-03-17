package com.mobileappt20.ui.add

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

enum class Action {
    CREATE_SCOPE_SUCCESS,
    CREATE_SCOPE_FAILED,
    NONE
}
data class ScopeIndexUiState(
    val scopeIndex: Int = 0,
)

data class CreateScopeUiState(
    val action: Action = Action.NONE,
    val documentReferenceId: String? = null
)

class AddScopeViewModel : ViewModel() {

    companion object {
        const val TAG = "AddScopeViewModel"
    }

    private val _scopeIndexUiState = MutableStateFlow(ScopeIndexUiState())
    val scopeIndexUiState: StateFlow<ScopeIndexUiState> = _scopeIndexUiState.asStateFlow()
    private val _createScopeUiState = MutableStateFlow(CreateScopeUiState())
    val createScopeUiState: StateFlow<CreateScopeUiState> = _createScopeUiState.asStateFlow()
    private val firestore = Firebase.firestore

    fun setScopeIndex(index: Int, delta: Int) {
        var scopeIndex = index + delta
        if (scopeIndex > 3) {
            scopeIndex = 0
        } else if (scopeIndex < 0) {
            scopeIndex = 3
        }

        _scopeIndexUiState.update { currentState ->
            currentState.copy(
                scopeIndex = scopeIndex
            )
        }
    }

    fun createScope(
        name: String,
        createTime: Long,
        scheduleTime: Long,
        collection: String
    ) {
        val scope = hashMapOf(
            "name" to name,
            "scheduleTime" to scheduleTime,
            "createTime" to createTime,
        )
        firestore.collection(collection)
            .add(scope)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "Scope added with ID: ${documentReference.id}")
                _createScopeUiState.update { currentState ->
                    currentState.copy(
                        action = Action.CREATE_SCOPE_SUCCESS,
                        documentReferenceId = documentReference.id
                    )
                }
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
                _createScopeUiState.update { currentState ->
                    currentState.copy(
                        action = Action.CREATE_SCOPE_FAILED,
                        documentReferenceId = null
                    )
                }
            }
    }
}