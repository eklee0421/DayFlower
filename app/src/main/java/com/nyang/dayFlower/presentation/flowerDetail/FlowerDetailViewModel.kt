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
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class FlowerDetailViewModel @Inject constructor(
    private val flowerDetailUseCase: GetFlowerDetailUseCase
) : ViewModel() {

    val flowerDetail = mutableStateOf(FlowerDetail())
    val localDate = mutableStateOf(LocalDate.now())

    init{
        viewModelScope.launch {
            getFlowerDetail()
        }
    }

    fun onEvent(event: FlowerDetailOnEvent){
        viewModelScope.launch {
            when (event) {
                is FlowerDetailOnEvent.SearchFlower -> {
                    localDate.value = LocalDate.of(localDate.value.year,event.month,event.day)
                    getFlowerDetail()
                }
                FlowerDetailOnEvent.SearchNextFlower -> {
                    localDate.value = localDate.value.plusDays(1)
                    getFlowerDetail()
                }
                FlowerDetailOnEvent.SearchPrevFlower -> {
                    localDate.value = localDate.value.minusDays(1)
                    getFlowerDetail()
                }
            }
        }
    }

    private suspend fun getFlowerDetail() {
        flowerDetailUseCase(
            requestFlowerDetail = RequestFlowerDetail(
                fMonth = localDate.value.month.value,
                fDay = localDate.value.dayOfMonth)).collect {
            flowerDetail.value = it
        }
    }

}