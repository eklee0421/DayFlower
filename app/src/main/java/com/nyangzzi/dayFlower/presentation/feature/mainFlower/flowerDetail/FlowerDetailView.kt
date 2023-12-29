package com.nyangzzi.dayFlower.presentation.feature.mainFlower.flowerDetail

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.VerticalPager
import com.google.accompanist.pager.rememberPagerState
import com.nyangzzi.dayFlower.domain.model.common.FlowerDetail
import com.nyangzzi.dayFlower.presentation.base.component.BaseDatePicker
import com.nyangzzi.dayFlower.presentation.feature.mainFlower.MainFlowerEvent
import kotlinx.coroutines.launch
import java.time.LocalDate


@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun FlowerDetailView(
    flowerDetail: FlowerDetail = FlowerDetail(),
    localDate: LocalDate = LocalDate.now(),
    isDatePicker: Boolean = false,
    onEvent: (MainFlowerEvent) -> Unit = {}
) {

    BaseDatePicker(isShown = isDatePicker, nowDate = localDate) {
        onEvent(MainFlowerEvent.SearchMainFlower(it.month.value, it.dayOfMonth))
    }

    Scaffold(
        topBar = {
            FlowerDetailTopBar(
                localDate = localDate,
                onEvent = onEvent
            )
        }
    ) {

        val pagerState = rememberPagerState()
        val coroutineScope = rememberCoroutineScope()

        VerticalPager(
            count = 2,
            state = pagerState,
            modifier = Modifier.padding(it)
        ) { page ->
            when (page) {
                0 -> FlowerCard(
                    flowerDetail = flowerDetail
                ) {
                    coroutineScope.launch { pagerState.animateScrollToPage(1) }
                }

                1 -> FlowerInfoView(
                    flowerDetail = flowerDetail
                ) {
                    coroutineScope.launch { pagerState.animateScrollToPage(0) }
                }
            }
        }
    }
}


@Preview
@Composable
private fun FlowerDetailPreview() {
    FlowerDetailView()
}

