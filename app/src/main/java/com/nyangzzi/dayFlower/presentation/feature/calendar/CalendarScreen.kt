package com.nyangzzi.dayFlower.presentation.feature.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.nyangzzi.dayFlower.data.network.ResultWrapper
import com.nyangzzi.dayFlower.domain.model.common.FlowerDetail
import com.nyangzzi.dayFlower.presentation.base.Utils
import com.nyangzzi.dayFlower.presentation.base.component.loadingShimmerEffect
import com.nyangzzi.dayFlower.ui.theme.Gray9
import com.nyangzzi.dayFlower.ui.theme.SystemBlue
import com.nyangzzi.dayFlower.ui.theme.SystemRed
import com.nyangzzi.dayFlower.ui.theme.White
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun CalendarScreen(viewModel: CalendarViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    CalendarContent(flowerMonth = uiState.flowerMonth, localDate = uiState.localDate)
}

@Composable
private fun CalendarContent(
    flowerMonth: ResultWrapper<List<FlowerDetail>> = ResultWrapper.Loading,
    localDate: LocalDate = LocalDate.now(),
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(White)
    ) {
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .fillMaxSize()
        ) {

            FlowerMonthTopBar(localDate = localDate, onPrevMonth = {}, onNextMonth = {})

            Column(modifier = Modifier.padding(horizontal = 20.dp)) {

                DayOfWeek()

                when (flowerMonth) {
                    is ResultWrapper.Error -> {
                        //todo
                    }

                    ResultWrapper.Loading -> {
                        LoadingFlowerCalendar()
                    }

                    is ResultWrapper.Success -> {
                        SuccessFlowerCalendar(
                            flowerMonth = flowerMonth.data,
                            onEvent = { },
                            firstDayOfWeek = LocalDate.of(
                                localDate.year,
                                localDate.month,
                                1
                            ).dayOfWeek.value
                        )
                    }
                }

            }
        }
    }

}

@Composable
fun FlowerMonthTopBar(
    localDate: LocalDate?,
    onPrevMonth: () -> Unit,
    onNextMonth: () -> Unit,
) {

    Box(
        Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {

        Text("${localDate?.year}년 ${localDate?.month?.value}월",
            modifier = Modifier
                .align(Alignment.Center)
                .clickable { /*onEvent(MainFlowerEvent.ShowDatePicker)*/ })

        IconButton(
            onClick = { onPrevMonth() },
            modifier = Modifier
                .size(56.dp)
                .align(Alignment.CenterStart)
        ) {
            Icon(
                imageVector = Icons.Rounded.KeyboardArrowLeft,
                contentDescription = ""
            )
        }

        IconButton(
            onClick = { onNextMonth() },
            modifier = Modifier
                .size(56.dp)
                .align(Alignment.CenterEnd)
        ) {
            Icon(
                imageVector = Icons.Rounded.KeyboardArrowRight,
                contentDescription = ""
            )
        }
    }
}

@Composable
fun DayOfWeek() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 22.dp)
    ) {
        DayOfWeek.values().forEach { dayOfWeek ->
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .weight(1f),
                text = dayOfWeek.getDisplayName(TextStyle.NARROW, Locale.KOREAN),
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center,
                color = when (dayOfWeek.value) {
                    6 -> SystemBlue
                    7 -> SystemRed
                    else -> Gray9
                }
            )
        }
    }
}

@Composable
private fun LoadingFlowerCalendar() {
    val brush = loadingShimmerEffect()
    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        for (i in 0 until 30) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp, horizontal = 4.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(shape = RoundedCornerShape(6.dp))
                            .background(brush)
                    )

                    Box(
                        modifier = Modifier
                            .height(14.dp)
                            .width(30.dp)
                            .clip(shape = RoundedCornerShape(6.dp))
                            .background(brush)
                    )
                }
            }
        }
    }
}

@Composable
private fun SuccessFlowerCalendar(
    flowerMonth: List<FlowerDetail>,
    onEvent: () -> Unit,
    firstDayOfWeek: Int
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {

        for (i in 1 until firstDayOfWeek) {
            item {
                Box {}
            }
        }

        itemsIndexed(
            flowerMonth.sortedWith(compareBy<FlowerDetail> { it.fMonth }.thenBy { it.fDay })
                .toList()
        ) { index, item ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp, horizontal = 4.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(Utils.setImageUrl(item.imgUrl1 ?: ""))
                        .crossfade(true)
                        .build(),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(shape = RoundedCornerShape(6.dp))
                )
                Text(
                    text = "${index + 1}",
                    style = MaterialTheme.typography.labelSmall,
                    color = when ((index + firstDayOfWeek - 1) % 7) {
                        5 -> SystemBlue
                        6 -> SystemRed
                        else -> Gray9
                    }
                )

            }
        }

    }
}
