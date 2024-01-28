package com.nyangzzi.dayFlower.presentation.feature.mainFlower


import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainFlowerViewModel @Inject constructor(
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainFlowerUiState())
    val uiState: StateFlow<MainFlowerUiState> = _uiState

}