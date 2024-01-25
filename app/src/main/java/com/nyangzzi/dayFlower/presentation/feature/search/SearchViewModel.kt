package com.nyangzzi.dayFlower.presentation.feature.search

import androidx.lifecycle.ViewModel
import com.nyangzzi.dayFlower.data.network.ResultWrapper
import com.nyangzzi.dayFlower.domain.model.flowerList.RequestFlowerList
import com.nyangzzi.dayFlower.domain.usecase.GetFlowerListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val flowerListUseCase: GetFlowerListUseCase
) : ViewModel() {


    private suspend fun getFlowerList(type: Int, word: String) {
        flowerListUseCase(
            requestFlowerList = RequestFlowerList(
                searchType = type,
                searchWord = word
            )
        ).collect { result ->
            when (result) {
                is ResultWrapper.Success -> {

                }

                is ResultWrapper.Error -> {

                }

                else -> {}
            }
        }
    }

}