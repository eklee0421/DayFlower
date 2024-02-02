package com.nyangzzi.dayFlower.presentation.base.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nyangzzi.dayFlower.presentation.base.util.noRippleClickable
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NumberPicker(
    sliderList: List<Int>,
    selectedData: Int,
    text: String = "",
    onChanged: (Int) -> Unit
) {

    val pagerState = rememberPagerState(sliderList.indexOf(selectedData))
    val scope = rememberCoroutineScope()

    LaunchedEffect(pagerState.currentPage) {
        onChanged(sliderList[pagerState.currentPage])
    }

    Box(
        modifier = Modifier
            .height(142.dp)
            .defaultMinSize(minWidth = 74.dp)
            .wrapContentWidth()
    ) {
        VerticalPager(
            pageCount = sliderList.size,
            state = pagerState,
            contentPadding = PaddingValues(vertical = 48.dp),
            pageSize = PageSize.Fixed(46.dp),
            modifier = Modifier.height(140.dp)
        ) { page ->
            val pageOffset =
                (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction

            val scaleFactor = 0.75f + (1f - 0.75f) * (1f - pageOffset.absoluteValue)
            val colorFactor = 0.35f + (1f - 0.35f) * (1f - pageOffset.absoluteValue)

            Box(modifier = Modifier
                .height(42.dp)
                .padding(horizontal = 16.dp)
                .noRippleClickable {
                    scope.launch {
                        pagerState.animateScrollToPage(page)
                    }
                }
                .graphicsLayer {
                    scaleX = scaleFactor
                    scaleY = scaleFactor
                }
                .alpha(
                    colorFactor.coerceIn(0f, 1f)
                ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "${sliderList[page]}$text",
                    modifier = Modifier,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}

@Composable
@Preview
fun NumberPickerPreview() {
    Box(modifier = Modifier.background(Color.White)) {
        NumberPicker(sliderList = listOf(1, 2, 3, 4, 5), selectedData = 1) {}
    }
}