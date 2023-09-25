package com.nyang.dayFlower.presentation.flowerDetail


import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyang.dayFlower.domain.model.flowerDetail.FlowerDetail
import com.nyang.dayFlower.domain.model.flowerDetail.RequestFlowerDetail
import com.nyang.dayFlower.domain.usecase.GetFlowerDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FlowerDetailViewModel @Inject constructor(
    private val flowerDetailUseCase: GetFlowerDetailUseCase
) : ViewModel() {

    val flowerDetail = mutableStateOf(FlowerDetail())

    init{
        viewModelScope.launch {
            getFlowerDetail()
        }
    }

    private suspend fun getFlowerDetail() {
        flowerDetailUseCase(requestFlowerDetail = RequestFlowerDetail()).collect {
            flowerDetail.value = it
        }
    }

}