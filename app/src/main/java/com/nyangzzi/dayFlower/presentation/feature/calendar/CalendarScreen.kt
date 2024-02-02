package com.nyangzzi.dayFlower.presentation.feature.calendar

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.nyangzzi.dayFlower.R
import com.nyangzzi.dayFlower.data.network.ResultWrapper
import com.nyangzzi.dayFlower.domain.model.common.FlowerDetail
import com.nyangzzi.dayFlower.presentation.base.dialog.DatePickerDialog
import com.nyangzzi.dayFlower.presentation.base.util.Utils
import com.nyangzzi.dayFlower.presentation.base.util.loadingShimmerEffect
import com.nyangzzi.dayFlower.presentation.feature.flowerDetail.FlowerDetailScreen
import com.nyangzzi.dayFlower.ui.theme.Gray11
import com.nyangzzi.dayFlower.ui.theme.Gray5
import com.nyangzzi.dayFlower.ui.theme.Gray9
import com.nyangzzi.dayFlower.ui.theme.Primary
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
    CalendarContent(
        flowerMonth = uiState.flowerMonth,
        isDetail = uiState.isDetail,
        isDatePicker = uiState.isDatePicker,
        localDate = uiState.localDate,
        onEvent = viewModel::onEvent
    )
}

@Composable
private fun CalendarContent(
    flowerMonth: ResultWrapper<List<FlowerDetail>> = ResultWrapper.Loading,
    isDetail: Boolean,
    isDatePicker: Boolean,
    localDate: LocalDate = LocalDate.now(),
    onEvent: (CalendarOnEvent) -> Unit
) {

    var selectedFlower by remember { mutableStateOf(FlowerDetail()) }

    if (isDetail) {
        FlowerDetailScreen(
            dataNo = selectedFlower.dataNo,
            onDismiss = { onEvent(CalendarOnEvent.SetDetailDialog(false)) })
    }

    DatePickerDialog(
        isShown = isDatePicker,
        year = localDate.year, month = localDate.monthValue, onConfirm = { year, month ->
            onEvent(CalendarOnEvent.SetDate(year = year, month = month))
        }) {
        onEvent(CalendarOnEvent.SetDatePickerDialog(false))
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(White)
            .padding(bottom = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .fillMaxSize()
        ) {

            FlowerMonthTopBar(
                localDate = localDate,
                onPrevMonth = { onEvent(CalendarOnEvent.OnPrevMonth) },
                onNextMonth = { onEvent(CalendarOnEvent.OnNextMonth) },
                setDatePicker = { onEvent(CalendarOnEvent.SetDatePickerDialog(true)) })

            Column(modifier = Modifier.padding(horizontal = 20.dp)) {

                DayOfWeek()

                when (flowerMonth) {
                    is ResultWrapper.Error -> {
                        ErrorFlowerCalendar(
                            msg = flowerMonth.errorMessage,
                            onRefresh = { onEvent(CalendarOnEvent.OnSearchMonth) })
                    }

                    ResultWrapper.Loading -> {
                        LoadingFlowerCalendar()
                    }

                    is ResultWrapper.Success -> {
                        SuccessFlowerCalendar(
                            flowerMonth = flowerMonth.data,
                            onSelected = {
                                selectedFlower = it
                                onEvent(CalendarOnEvent.SetDetailDialog(true))
                            },
                            firstDayOfWeek = LocalDate.of(
                                localDate.year,
                                localDate.month,
                                1
                            ).dayOfWeek.value
                        )
                    }

                    ResultWrapper.None -> {}
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
    setDatePicker: () -> Unit
) {

    Box(
        Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .height(68.dp),
    ) {

        Text(
            "${localDate?.year}년 ${localDate?.month?.value}월",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(4.dp)
                .clip(shape = RoundedCornerShape(4.dp))
                .clickable {
                    setDatePicker()
                }
        )

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
        //요일 순서 일요일부터
        listOf(
            DayOfWeek.SUNDAY,
            DayOfWeek.MONDAY,
            DayOfWeek.TUESDAY,
            DayOfWeek.WEDNESDAY,
            DayOfWeek.THURSDAY,
            DayOfWeek.FRIDAY,
            DayOfWeek.SATURDAY
        ).forEach { dayOfWeek ->
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .weight(1f),
                text = dayOfWeek.getDisplayName(TextStyle.NARROW, Locale.KOREAN),
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center,
                color = when (dayOfWeek) {
                    DayOfWeek.SATURDAY -> SystemBlue
                    DayOfWeek.SUNDAY -> SystemRed
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
private fun ErrorFlowerCalendar(msg: String?, onRefresh: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.ic_error_info), contentDescription = null)
        Text(text = msg ?: "오류가 발생했습니다", color = Gray5, style = MaterialTheme.typography.bodyMedium)

        Button(
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Primary,
                contentColor = Gray11
            ),
            onClick = { onRefresh() }) {
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Icon(imageVector = Icons.Rounded.Refresh, contentDescription = null)
                Text(text = "재시도")
            }

        }
    }
}

@Composable
private fun SuccessFlowerCalendar(
    flowerMonth: List<FlowerDetail>,
    onSelected: (FlowerDetail) -> Unit,
    firstDayOfWeek: Int
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {

        for (i in 0 until firstDayOfWeek) {
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
                    .clickable { onSelected(item) }
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
                        .clip(shape = RoundedCornerShape(6.dp)),
                    placeholder = painterResource(id = R.drawable.ic_loading_image)
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
