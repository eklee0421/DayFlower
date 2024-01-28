package com.nyangzzi.dayFlower.presentation.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyangzzi.dayFlower.data.network.ResultWrapper
import com.nyangzzi.dayFlower.domain.model.flowerList.RequestFlowerList
import com.nyangzzi.dayFlower.domain.usecase.GetFlowerListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val flowerListUseCase: GetFlowerListUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState

    private var job: Job? = null
    fun onEvent(event: SearchEvent) {
        viewModelScope.launch {
            when (event) {
                is SearchEvent.SearchFlowerList -> getFlowerList()
                is SearchEvent.UpdateSelectedType -> updateSelectedType(event.selectedType)
                is SearchEvent.UpdateSearchWord -> updateSearchWord(event.text)
                is SearchEvent.ClearFlowerList -> clearFlowerList()
                is SearchEvent.SetShowDetail -> _uiState.update { it.copy(isShowDetail = event.isShown) }
            }
        }
    }

    private fun clearFlowerList() {
        job?.cancel()
        _uiState.update {
            it.copy(
                flowerList = ResultWrapper.None,
                searchWord = ""
            )
        }
    }

    private fun updateSelectedType(selectedType: SearchTapType) {
        _uiState.update {
            it.copy(
                selectedType = selectedType
            )
        }
    }

    private fun updateSearchWord(text: String) {
        _uiState.update {
            it.copy(
                searchWord = text
            )
        }
    }

    private suspend fun getFlowerList() {
        job = viewModelScope.launch {
            flowerListUseCase(
                requestFlowerList = RequestFlowerList(
                    searchType = uiState.value.selectedType.type,
                    searchWord = uiState.value.searchWord
                )
            ).collect { result ->
                _uiState.update { it.copy(flowerList = result) }
            }
        }
    }

}