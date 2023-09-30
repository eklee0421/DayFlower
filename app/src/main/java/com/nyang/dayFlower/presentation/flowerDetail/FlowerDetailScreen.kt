package com.nyang.dayFlower.presentation.flowerDetail

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.VerticalPager
import com.google.accompanist.pager.rememberPagerState
import com.nyang.dayFlower.domain.model.common.FlowerDetail
import com.nyang.dayFlower.presentation.base.BaseDatePicker
import com.nyang.dayFlower.presentation.flowerDetail.component.FlowerCard
import com.nyang.dayFlower.presentation.flowerDetail.component.FlowerDetailTopBar
import com.nyang.dayFlower.presentation.flowerDetail.component.FlowerInfoView
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun FlowerDetailScreen(
    viewModel : FlowerDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    FlowerDetailContent(
        flowerDetail = uiState.flowerDetail,
        localDate = uiState.localDate,
        isDatePicker = uiState.isDatePicker,
        onEvent = viewModel::onEvent
        )

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
        topBar = { FlowerDetailTopBar(localDate = localDate, onEvent = onEvent)}
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

