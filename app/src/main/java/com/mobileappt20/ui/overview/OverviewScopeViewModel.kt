package com.mobileappt20.ui.overview

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

enum class Action {
    RETRIEVE_SCOPE_SUCCESS,
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

    private val _retrieveScopeUiState = MutableStateFlow(RetrieveScopeUiState())
    val retrieveScopeUiState: StateFlow<RetrieveScopeUiState> = _retrieveScopeUiState.asStateFlow()
    private val firestore = Firebase.firestore

    fun retrieveScope(collection: String) {
        firestore.collection(collection)
            .get()
            .addOnSuccessListener { querySnapshot ->
                Log.d(TAG, "Retrieve documents: ${querySnapshot.documents}")
                _retrieveScopeUiState.update { currentState ->
                    currentState.copy(
                        action = Action.RETRIEVE_SCOPE_SUCCESS,
                        documents = querySnapshot.documents
                    )
                }
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error retrieve documents", e)
                _retrieveScopeUiState.update { currentState ->
                    currentState.copy(
                        action = Action.RETRIEVE_SCOPE_FAILED,
                        documents = listOf()
                    )
                }
            }
    }
}