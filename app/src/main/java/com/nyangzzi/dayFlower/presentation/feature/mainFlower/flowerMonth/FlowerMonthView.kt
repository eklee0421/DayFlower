package com.nyangzzi.dayFlower.presentation.feature.mainFlower.flowerMonth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nyangzzi.dayFlower.domain.model.common.FlowerDetail
import com.nyangzzi.dayFlower.presentation.feature.mainFlower.MainFlowerEvent
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlowerMonthView(
    flowerMonth: List<FlowerDetail> = emptyList(),
    localDate: LocalDate = LocalDate.now(),
    isDatePicker: Boolean = false,
    onEvent: (MainFlowerEvent) -> Unit = {}
) {

    Scaffold(
        topBar = {
            FlowerMonthTopBar(
                localDate = localDate,
                onEvent = onEvent
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp)
        ) {
            FlowerCalendar(flowerMonth = flowerMonth, onEvent = onEvent)
        }
    }

}

@Preview
@Composable
private fun FlowerMonthPreview() {
    FlowerMonthView()
}

