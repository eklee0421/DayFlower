package com.nyang.dayFlower.presentation.flowerDetail


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyang.dayFlower.domain.model.flowerDetail.RequestFlowerDetail
import com.nyang.dayFlower.domain.usecase.GetFlowerDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlowerDetailViewModel @Inject constructor(
    private val flowerDetailUseCase: GetFlowerDetailUseCase
) : ViewModel() {
    init{
        viewModelScope.launch {
            flowerDetailUseCase(requestFlowerDetail = RequestFlowerDetail()).collect()
        }
    }

}