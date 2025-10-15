/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.dessertclicker.ui

import androidx.lifecycle.ViewModel
import com.example.dessertclicker.data.Datasource
import com.example.dessertclicker.model.Dessert
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * ViewModel for Dessert Clicker app
 * Manages UI state and business logic for the dessert clicker game
 */
class DessertViewModel : ViewModel() {

    // Backing property to avoid state updates from other classes
    private val _uiState = MutableStateFlow(DessertUiState())
    
    // The UI collects from this StateFlow to get its state updates
    val uiState: StateFlow<DessertUiState> = _uiState.asStateFlow()

    private val desserts: List<Dessert> = Datasource.dessertList

    init {
        // Initialize with the first dessert
        resetGame()
    }

    /**
     * Reset the game to initial state
     */
    fun resetGame() {
        _uiState.value = DessertUiState(
            currentDessertIndex = 0,
            currentDessertPrice = desserts[0].price,
            currentDessertImageId = desserts[0].imageId,
            revenue = 0,
            dessertsSold = 0
        )
    }

    /**
     * Handle dessert click event
     * Updates revenue, desserts sold, and current dessert to show
     */
    fun onDessertClicked() {
        _uiState.update { currentState ->
            val newDessertsSold = currentState.dessertsSold + 1
            val newRevenue = currentState.revenue + currentState.currentDessertPrice
            val dessertToShow = determineDessertToShow(newDessertsSold)
            
            currentState.copy(
                revenue = newRevenue,
                dessertsSold = newDessertsSold,
                currentDessertImageId = dessertToShow.imageId,
                currentDessertPrice = dessertToShow.price
            )
        }
    }

    /**
     * Determine which dessert to show based on desserts sold
     */
    private fun determineDessertToShow(dessertsSold: Int): Dessert {
        var dessertToShow = desserts.first()
        for (dessert in desserts) {
            if (dessertsSold >= dessert.startProductionAmount) {
                dessertToShow = dessert
            } else {
                // The list of desserts is sorted by startProductionAmount
                // Break as soon as we see a dessert whose startProductionAmount
                // is greater than the amount sold
                break
            }
        }
        return dessertToShow
    }
}
