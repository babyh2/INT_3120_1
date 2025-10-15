package com.example.amphibians.ui

import com.example.amphibians.model.Amphibian

/**
 * UI state cho màn hình Amphibians
 */
sealed interface AmphibiansUiState {
    data class Success(val amphibians: List<Amphibian>) : AmphibiansUiState
    object Error : AmphibiansUiState
    object Loading : AmphibiansUiState
}
