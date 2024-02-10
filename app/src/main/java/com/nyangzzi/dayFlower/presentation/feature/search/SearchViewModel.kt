package com.nyangzzi.dayFlower.presentation.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyangzzi.dayFlower.data.network.ResultWrapper
import com.nyangzzi.dayFlower.domain.model.common.FlowerDetail
import com.nyangzzi.dayFlower.domain.model.flowerList.RequestFlowerList
import com.nyangzzi.dayFlower.domain.usecase.datastore.RecentSearchMeanUseCase
import com.nyangzzi.dayFlower.domain.usecase.datastore.RecentSearchNameUseCase
import com.nyangzzi.dayFlower.domain.usecase.firebase.FirebaseManager
import com.nyangzzi.dayFlower.domain.usecase.firebase.UpdateLockerUseCase
import com.nyangzzi.dayFlower.domain.usecase.network.GetFlowerListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Integer.min
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val flowerListUseCase: GetFlowerListUseCase,
    private val recentSearchNameUseCase: RecentSearchNameUseCase,
    private val recentSearchMeanUseCase: RecentSearchMeanUseCase,
    private val firebaseManager: FirebaseManager,
    private val updateLockerUseCase: UpdateLockerUseCase,
) : ViewModel() {

    private val _recentName = recentSearchNameUseCase.recentName
    private val _recentMean = recentSearchMeanUseCase.recentMean

    private val _savedFlower = firebaseManager.locker()

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> =
        combine(
            _uiState,
            _recentName,
            _recentMean,
            _savedFlower
        ) { state, recentName, recentMean, savedFlower ->
            state.copy(
                recentName = recentName,
                recentMean = recentMean,
                savedFlower = savedFlower
            )
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            SearchUiState()
        )

    private var job: Job? = null

    init {
        viewModelScope.launch {
            firebaseManager.searchWords().collect { result ->
                _uiState.update {
                    it.copy(
                        recommendedWords = result
                    )
                }
            }
        }
    }

    fun onEvent(event: SearchEvent) {
        viewModelScope.launch {
            when (event) {
                is SearchEvent.SearchFlowerList -> getFlowerList()
                is SearchEvent.UpdateSelectedType -> updateSelectedType(event.selectedType)
                is SearchEvent.UpdateSearchWord -> updateSearchWord(event.text)
                is SearchEvent.ClearFlowerList -> clearFlowerList()
                is SearchEvent.SetShowDetail -> _uiState.update { it.copy(isShowDetail = event.isShown) }
                is SearchEvent.AddRecentWord -> addRecentWord()
                is SearchEvent.RemoveRecentWord -> removeRecentWord(
                    item = event.item,
                    all = event.all
                )

                is SearchEvent.UpdateLocker -> updateFlower(
                    isSaved = event.isSaved,
                    flower = event.flowerDetail
                )
            }
        }
    }

    private suspend fun removeRecentWord(item: String?, all: Boolean) {

        when (uiState.value.selectedType) {
            SearchTapType.NAME -> {
                recentSearchNameUseCase.setRentName(
                    if (all) {
                        emptyList()
                    } else {
                        uiState.value.recentName.toMutableList().apply {
                            remove(item)
                        }.toList()
                    }
                )
            }

            SearchTapType.MEAN -> {
                recentSearchMeanUseCase.setRentMean(
                    if (all) {
                        emptyList()
                    } else {
                        uiState.value.recentMean.toMutableList().apply {
                            remove(item)
                        }.toList()
                    }
                )
            }
        }
    }

    private suspend fun addRecentWord() {
        when (uiState.value.selectedType) {
            SearchTapType.NAME -> {
                val addName = uiState.value.recentName.toMutableList().apply {
                    add(0, uiState.value.searchWord)
                }.toList().distinct()
                recentSearchNameUseCase.setRentName(addName.subList(0, min(10, addName.size)))
            }

            SearchTapType.MEAN -> {
                val addMean = uiState.value.recentMean.toMutableList().apply {
                    add(0, uiState.value.searchWord)
                }.toList().distinct()
                recentSearchMeanUseCase.setRentMean(addMean.subList(0, min(10, addMean.size)))
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
                selectedType = selectedType,
                searchWord = ""
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
            launch {
                addRecentWord()
            }
            launch {
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

    private suspend fun updateFlower(isSaved: Boolean, flower: FlowerDetail) {
        updateLockerUseCase(
            isSaved = isSaved,
            flower = flower
        ).collect { result ->
            when (result) {
                is ResultWrapper.Success -> {

                }

                else -> {}
            }

        }
    }
}