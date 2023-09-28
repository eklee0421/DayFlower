package com.nyang.dayFlower.presentation.flowerDetail

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.VerticalPager
import com.google.accompanist.pager.rememberPagerState
import com.nyang.dayFlower.domain.model.flowerDetail.FlowerDetail
import com.nyang.dayFlower.presentation.flowerDetail.component.FlowerCard
import com.nyang.dayFlower.presentation.flowerDetail.component.FlowerInfoView
import com.nyang.dayFlower.ui.theme.Tertiary10
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Calendar

@Composable
fun FlowerDetailScreen(
    viewModel : FlowerDetailViewModel = hiltViewModel()
) {

    FlowerDetailContent(
        flowerDetail = viewModel.flowerDetail.value,
        localDate = viewModel.localDate.value,
        onEvent = viewModel::onEvent
        )

}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
private fun FlowerDetailContent(
    flowerDetail : FlowerDetail = FlowerDetail(),
    localDate: LocalDate? = null,
    onEvent: (FlowerDetailOnEvent) -> Unit = {}
) {
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

@Composable
private fun FlowerDetailTopBar(
    localDate: LocalDate?,
    onEvent: (FlowerDetailOnEvent) -> Unit = {}
){
    Box(
        Modifier
            .fillMaxWidth()
            .height(56.dp)){

        Divider(color = MaterialTheme.colorScheme.outlineVariant)

        Text("${localDate?.month?.value}월 ${localDate?.dayOfMonth}일",
            modifier = Modifier
                .align(Alignment.Center)
                .clickable {  })

        IconButton(onClick = { onEvent(FlowerDetailOnEvent.SearchPrevFlower) },
            modifier = Modifier
                .size(56.dp)
                .align(Alignment.CenterStart)) {
            Icon(imageVector= Icons.Rounded.KeyboardArrowLeft,
                contentDescription = "")
        }

        IconButton(onClick = { onEvent(FlowerDetailOnEvent.SearchNextFlower)  },
            modifier = Modifier
                .size(56.dp)
                .align(Alignment.CenterEnd)) {
            Icon(imageVector= Icons.Rounded.KeyboardArrowRight,
                contentDescription = "")
        }
    }
}

@Composable
private fun FlowerDatePicker(selectDate: (LocalDate)->Unit){
    val calendar = Calendar.getInstance()
    DatePickerDialog(
        LocalContext.current,
        { _, _, month, day ->
            selectDate(LocalDate.of(LocalDate.now().year, month, day))
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH),
        )
}

@Preview
@Composable
private fun FlowerDetailPreview() {
    FlowerDetailContent()
}

