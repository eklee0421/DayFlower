package com.nyang.dayFlower.presentation.feature.mainFlower.flowerMonth

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.VerticalPager
import com.google.accompanist.pager.rememberPagerState
import com.nyang.dayFlower.domain.model.common.FlowerDetail
import com.nyang.dayFlower.presentation.base.component.BaseDatePicker
import com.nyang.dayFlower.presentation.feature.mainFlower.MainFlowerEvent
import kotlinx.coroutines.launch
import java.time.LocalDate


@Composable
fun FlowerMonthView(flowerMonth: List<FlowerDetail> = emptyList()) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        itemsIndexed(flowerMonth) { index, flowerDetail ->
            Text(text = flowerDetail.flowNm.toString())
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Preview
@Composable
private fun FlowerMonthPreview() {
    FlowerMonthView()
}

