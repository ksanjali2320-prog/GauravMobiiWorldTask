package com.gks.gauravmobiiworldtask.domain.model

sealed class UiState {
    object Loading : UiState()
    data class Success(val repos: List<Repository>) : UiState()
    data class Error(val message: String) : UiState()
}
