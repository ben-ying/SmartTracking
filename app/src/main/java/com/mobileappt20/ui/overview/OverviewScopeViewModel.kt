package com.mobileappt20.ui.overview

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mobileappt20.data.Scope.Companion.CREATE_TIME
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

enum class Action {
    RETRIEVE_SCOPE_SUCCESS,
    RETRIEVE_SCOPE_COMPLETE,
    RETRIEVE_SCOPE_FAILED,
    NONE
}

data class RetrieveScopeUiState(
    val action: Action = Action.NONE,
    val documents: List<DocumentSnapshot> = listOf()
)

class OverviewScopeViewModel : ViewModel() {

    companion object {
        const val TAG = "OverviewScopeViewModel"
    }

    data class ScopeIndexUiState(
        val scopeIndex: Int = 0,
    )

    private val _scopeIndexUiState = MutableStateFlow(ScopeIndexUiState(0))
    val scopeIndexUiState: StateFlow<ScopeIndexUiState> = _scopeIndexUiState.asStateFlow()
    private val _retrieveScopeUiState = MutableStateFlow(RetrieveScopeUiState())
    val retrieveScopeUiState: StateFlow<RetrieveScopeUiState> = _retrieveScopeUiState.asStateFlow()
    private val firestore = Firebase.firestore
    private var documents = listOf<DocumentSnapshot>()

    fun setScopeIndex(index: Int) {
        if (documents.size < 2) return
        var scopeIndex = index

        if (scopeIndex < 0) {
            scopeIndex = documents.size - 1
        } else if (scopeIndex > documents.size - 1) {
            scopeIndex = 0
        }

        _scopeIndexUiState.update { currentState ->
            currentState.copy(
                scopeIndex = scopeIndex,
            )
        }
    }

    fun retrieveScope(collection: String) {
        firestore.collection(collection).orderBy(CREATE_TIME, Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { querySnapshot ->
                Log.d(TAG, "Retrieve documents: ${querySnapshot.documents}")
                _retrieveScopeUiState.update { currentState ->
                    documents = querySnapshot.documents
                    currentState.copy(
                        action = Action.RETRIEVE_SCOPE_SUCCESS,
                        documents = documents
                    )
                }
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error retrieve documents", e)
                _retrieveScopeUiState.update { currentState ->
                    documents = listOf()
                    currentState.copy(
                        action = Action.RETRIEVE_SCOPE_FAILED,
                        documents = documents
                    )
                }
            }
            .addOnCompleteListener {
                _retrieveScopeUiState.update { currentState ->
                    currentState.copy(
                        action = Action.RETRIEVE_SCOPE_COMPLETE,
                    )
                }
            }
    }
}