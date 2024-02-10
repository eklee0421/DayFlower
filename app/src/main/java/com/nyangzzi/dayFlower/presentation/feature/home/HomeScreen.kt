package com.nyangzzi.dayFlower.presentation.feature.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nyangzzi.dayFlower.R
import com.nyangzzi.dayFlower.data.network.ResultWrapper
import com.nyangzzi.dayFlower.domain.model.common.FlowerDetail
import com.nyangzzi.dayFlower.presentation.base.component.FlowerCard
import com.nyangzzi.dayFlower.presentation.base.component.FlowerCardSize
import com.nyangzzi.dayFlower.presentation.base.util.noRippleClickable
import com.nyangzzi.dayFlower.ui.theme.Gray10
import com.nyangzzi.dayFlower.ui.theme.Gray11
import com.nyangzzi.dayFlower.ui.theme.White
import kotlinx.coroutines.launch
import java.time.LocalDate
import kotlin.math.absoluteValue

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(White)
    ) {
        Image(
            painterResource(id = R.drawable.img_home_top_background),
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth(),
            contentDescription = null
        )

        HomeContent(uiState = uiState, onEvent = viewModel::onEvent)
    }

}

@Composable
private fun HomeContent(uiState: HomeUiState, onEvent: (HomeEvent) -> Unit) {

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        HomeTop(uiState = uiState)
        Spacer(modifier = Modifier.height(14.dp))
        TodayFlower(
            localDate = uiState.localDate,
            flowerDetail = uiState.flowerDetail,
            onRefresh = { onEvent(HomeEvent.GetDayFlower) },
            isShowDetail = uiState.isShowDetail && uiState.isShowDataNo == null,
            savedFlower = uiState.savedFlower,
            setShowDetail = { onEvent(HomeEvent.SetShowDetail(it)) },
            updateLocker = { isSaved, flower -> onEvent(HomeEvent.UpdateLocker(isSaved, flower)) })

        if (uiState.recentViewFlower.isNotEmpty()) {
            Spacer(modifier = Modifier.height(14.dp))
            RecentViewFlower(recentViewFlower = uiState.recentViewFlower,
                onRefresh = { onEvent(HomeEvent.GetDayFlower) },
                isShowDetail = uiState.isShowDetail,
                savedFlower = uiState.savedFlower,
                isShowDataNo = uiState.isShowDataNo ?: -1,
                setShowDetail = { isShown, dataNo ->
                    onEvent(
                        HomeEvent.SetShowDetail(
                            isShown,
                            dataNo
                        )
                    )
                },
                updateLocker = { isSaved, flower ->
                    onEvent(
                        HomeEvent.UpdateLocker(
                            isSaved,
                            flower
                        )
                    )
                })
        }

    }
}

@Composable
private fun HomeTop(uiState: HomeUiState) {

    Box(modifier = Modifier.fillMaxWidth()) {

        Image(
            painter = painterResource(id = R.drawable.img_home_flower_group),
            contentDescription = "flowers",
            modifier = Modifier.padding(top = 26.dp, start = 20.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.img_home_leaf_group),
            contentDescription = "leaves",
            modifier = Modifier
                .padding(top = 55.dp)
                .align(Alignment.TopEnd)
        )

        Text(
            text = "${uiState.user?.displayName}님, 오늘도 좋은 날이에요.\n오늘의 꽃을 추천드릴게요.",
            style = MaterialTheme.typography.headlineSmall,
            color = Gray11,
            modifier = Modifier.padding(top = 65.dp, start = 20.dp)
        )
    }
}

@Composable
private fun TodayFlower(
    localDate: LocalDate,
    flowerDetail: ResultWrapper<FlowerDetail>,
    onRefresh: () -> Unit,
    savedFlower: List<FlowerDetail>,
    isShowDetail: Boolean,
    setShowDetail: (Boolean) -> Unit,
    updateLocker: (Boolean, FlowerDetail) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .padding(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Text(
            text = "${localDate.year}년 ${localDate.month?.value}월 ${localDate.dayOfMonth}일",
            style = MaterialTheme.typography.titleMedium,
            color = Gray10
        )

        FlowerCard(
            flower = flowerDetail,
            onRefresh = onRefresh,
            savedFlower = savedFlower,
            isShowDetail = isShowDetail,
            setShowDetail = { it, _ -> setShowDetail(it) },
            onSave = updateLocker
        )

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun RecentViewFlower(
    recentViewFlower: List<FlowerDetail>,
    onRefresh: () -> Unit,
    savedFlower: List<FlowerDetail>,
    isShowDetail: Boolean,
    isShowDataNo: Int,
    setShowDetail: (Boolean, Int) -> Unit,
    updateLocker: (Boolean, FlowerDetail) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .padding(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "방금 조회한 꽃",
            style = MaterialTheme.typography.titleMedium,
            color = Gray10
        )

        val pagerState = rememberPagerState(0)
        val scope = rememberCoroutineScope()

        var recentViewFlowerList by remember {
            mutableStateOf(emptyList<FlowerDetail>())
        }

        LaunchedEffect(key1 = isShowDetail, block = {
            if (!isShowDetail) {
                recentViewFlowerList = recentViewFlower
                pagerState.animateScrollToPage(0)
            }
        })

        Box(
            modifier = Modifier.wrapContentSize()
        ) {
            HorizontalPager(
                pageCount = recentViewFlowerList.size,
                state = pagerState,
                contentPadding = PaddingValues(horizontal = 66.dp),
            ) { page ->
                val pageOffset =
                    (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction

                val scaleFactor = 0.85f + (1f - 0.85f) * (1f - pageOffset.absoluteValue)

                Box(modifier = Modifier
                    .noRippleClickable {
                        scope.launch {
                            pagerState.animateScrollToPage(page)
                        }
                    }
                    .graphicsLayer {
                        scaleX = scaleFactor
                        scaleY = scaleFactor
                    },
                    contentAlignment = Alignment.Center
                ) {

                    FlowerCard(
                        flower = ResultWrapper.Success(recentViewFlowerList[page]),
                        onRefresh = onRefresh,
                        savedFlower = savedFlower,
                        isShowDetail = isShowDetail && recentViewFlowerList[page].dataNo == isShowDataNo,
                        setShowDetail = { isShown, dataNo ->
                            scope.launch {
                                setShowDetail(isShown, dataNo)
                            }
                        },
                        onSave = updateLocker,
                        cardSize = FlowerCardSize.SMALL
                    )

                }
            }
        }

    }

}