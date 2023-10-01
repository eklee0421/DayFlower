package com.nyang.dayFlower.presentation.feature.flowerDetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.VerticalPager
import com.google.accompanist.pager.rememberPagerState
import com.nyang.dayFlower.R
import com.nyang.dayFlower.domain.model.common.FlowerDetail
import com.nyang.dayFlower.domain.model.flowerMonth.RequestFlowerMonth
import com.nyang.dayFlower.presentation.base.BaseDatePicker
import com.nyang.dayFlower.presentation.feature.flowerDetail.component.FlowerCard
import com.nyang.dayFlower.presentation.feature.flowerDetail.component.FlowerDetailTopBar
import com.nyang.dayFlower.presentation.feature.flowerDetail.component.FlowerInfoView
import com.nyang.dayFlower.presentation.navigation.Screens
import com.nyang.dayFlower.presentation.navigation.onNavigateNext
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun FlowerDetailScreen(
    viewModel : FlowerDetailViewModel = hiltViewModel()
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
    onEvent: (FlowerDetailEvent) -> Unit = {}) {

    Scaffold(topBar = {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp)
                .height(56.dp)
        ) {
            Text("하루, 꽃", modifier = Modifier.align(Alignment.Center))

            IconButton(onClick = {
                onEvent(FlowerDetailEvent.IsChangeView(isCalendar = !isCalendar))
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
                FlowerCalendarScreen(flowerMonth= flowerMonth)
            } else {
                FlowerDetailContent(
                    flowerDetail = flowerDetail,
                    localDate = localDate,
                    isDatePicker = isDatePicker,
                    onEvent = onEvent
                )
            }
        }
    }
}

@Composable
fun FlowerCalendarScreen(flowerMonth: List<FlowerDetail>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        itemsIndexed(flowerMonth) { index, flowerDetail ->
            Text(text = flowerDetail.flowNm.toString())
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
private fun FlowerDetailContent(
    flowerDetail : FlowerDetail = FlowerDetail(),
    localDate: LocalDate = LocalDate.now(),
    isDatePicker: Boolean = false,
    onEvent: (FlowerDetailEvent) -> Unit = {}
) {

    BaseDatePicker(isShown = isDatePicker, nowDate = localDate) {
        onEvent(FlowerDetailEvent.SearchFlower(it.month.value, it.dayOfMonth))
    }

    Scaffold(
        topBar = { FlowerDetailTopBar(localDate = localDate, onEvent = onEvent) }
    ) {

        val pagerState = rememberPagerState()
        val coroutineScope = rememberCoroutineScope()

        VerticalPager(
            count = 2,
            state = pagerState,
            modifier = Modifier.padding(it)) { page ->
            when(page){
                0 -> FlowerCard(flowerDetail = flowerDetail) {
                    coroutineScope.launch { pagerState.animateScrollToPage(1) } }
                1 -> FlowerInfoView(flowerDetail = flowerDetail){
                    coroutineScope.launch { pagerState.animateScrollToPage(0) } }
            }
        }
    }
}





@Preview
@Composable
private fun FlowerDetailPreview() {
    FlowerDetailContent()
}

