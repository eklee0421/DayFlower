package com.nyang.dayFlower.presentation.feature.mainFlower

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nyang.dayFlower.R
import com.nyang.dayFlower.domain.model.common.FlowerDetail
import com.nyang.dayFlower.presentation.feature.mainFlower.flowerDetail.FlowerDetailView
import com.nyang.dayFlower.presentation.feature.mainFlower.flowerMonth.FlowerMonthView
import java.time.LocalDate

@Composable
fun MainFlowerScreen(
    viewModel : MainFlowerViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    FlowerBaseContent(
        flowerDetail = uiState.flowerDetail,
        flowerMonth= uiState.flowerMonth,
        localDate = uiState.localDate,
        isDatePicker = uiState.isDatePicker,
        isCalendar = uiState.isCalendar,
        onEvent = viewModel::onEvent
        )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FlowerBaseContent(
    flowerDetail : FlowerDetail = FlowerDetail(),
    flowerMonth: List<FlowerDetail> = emptyList(),
    localDate: LocalDate = LocalDate.now(),
    isDatePicker: Boolean = false,
    isCalendar: Boolean = false,
    onEvent: (MainFlowerEvent) -> Unit = {}) {

    Scaffold(topBar = {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp)
                .height(56.dp)
        ) {
            Text("하루, 꽃", modifier = Modifier.align(Alignment.Center))

            IconButton(onClick = {
                onEvent(MainFlowerEvent.IsChangeView(isCalendar = !isCalendar))
            }, modifier = Modifier.align(Alignment.CenterEnd)) {
                Image(
                    painter = painterResource(id = if (!isCalendar) R.drawable.ic_calendar else R.drawable.ic_day),
                    contentDescription = null
                )
            }
        }

    }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            if (isCalendar) {
                FlowerMonthView(
                    flowerMonth = flowerMonth,
                    localDate = localDate,
                    onEvent = onEvent)
            } else {
                FlowerDetailView(
                    flowerDetail = flowerDetail,
                    localDate = localDate,
                    isDatePicker = isDatePicker,
                    onEvent = onEvent
                )
            }
        }
    }
}
