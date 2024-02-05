package com.nyangzzi.dayFlower.presentation.feature.flowerDetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.nyangzzi.dayFlower.R
import com.nyangzzi.dayFlower.data.network.ResultWrapper
import com.nyangzzi.dayFlower.domain.model.common.FlowerDetail
import com.nyangzzi.dayFlower.presentation.base.component.Badge
import com.nyangzzi.dayFlower.presentation.base.util.Utils
import com.nyangzzi.dayFlower.presentation.base.util.loadingShimmerEffect
import com.nyangzzi.dayFlower.presentation.base.util.noRippleClickable
import com.nyangzzi.dayFlower.ui.theme.Gray10
import com.nyangzzi.dayFlower.ui.theme.Gray11
import com.nyangzzi.dayFlower.ui.theme.Gray4
import com.nyangzzi.dayFlower.ui.theme.Gray5
import com.nyangzzi.dayFlower.ui.theme.Gray6
import com.nyangzzi.dayFlower.ui.theme.Gray9
import com.nyangzzi.dayFlower.ui.theme.Primary
import com.nyangzzi.dayFlower.ui.theme.PrimaryAlpha50
import com.nyangzzi.dayFlower.ui.theme.SystemRed
import com.nyangzzi.dayFlower.ui.theme.White

@Composable
fun FlowerDetailScreen(
    dataNo: Int?,
    onDismiss: () -> Unit,
    viewModel: FlowerDetailViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = dataNo) {
        viewModel.onEvent(FlowerDetailOnEvent.OnSearchDetail(dataNo))
    }

    Dialog(
        onDismissRequest = {
            //onDismiss()
            //viewModel.onEvent(FlowerDetailOnEvent.OnDismiss)
        },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        )
    ) {
        Content(
            flowerDetail = uiState.flowerDetail,
            isSaved = uiState.isSaved,
            onRefresh = {
                viewModel.onEvent(FlowerDetailOnEvent.OnSearchDetail(dataNo))
            },
            onDismiss = {
                onDismiss()
                viewModel.onEvent(FlowerDetailOnEvent.OnDismiss)
            },
            onSave = {
                viewModel.onEvent(FlowerDetailOnEvent.UpdateLocker)
            }
        )
    }

}

@Composable
private fun Content(
    flowerDetail: ResultWrapper<FlowerDetail>,
    isSaved: Boolean,
    onRefresh: () -> Unit,
    onDismiss: () -> Unit,
    onSave: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White)
    ) {
        Top(onDismiss = onDismiss)
        when (flowerDetail) {
            is ResultWrapper.Error -> {
                ErrorContent(flowerDetail.errorMessage, onRefresh = onRefresh)
            }

            is ResultWrapper.Loading -> {
                LoadingContent()
            }

            is ResultWrapper.Success -> {
                SuccessContent(flowerDetail.data, isSaved = isSaved, onSave = onSave)
            }

            ResultWrapper.None -> {}
        }
    }
}


@Composable
private fun Top(onDismiss: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(68.dp)
    ) {
        IconButton(onClick = onDismiss, modifier = Modifier.size(68.dp)) {
            Image(painter = painterResource(id = R.drawable.ic_close), contentDescription = null)
        }
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.titleMedium,
            color = Gray11,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
private fun ErrorContent(msg: String?, onRefresh: () -> Unit) {
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
private fun LoadingContent() {

    val brush = loadingShimmerEffect()
    Column(
        modifier = Modifier.verticalScroll(
            rememberScrollState()
        )
    ) {
        Box(
            modifier = Modifier
                .height(58.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(132.dp, 20.dp)
                    .background(brush = brush, shape = RoundedCornerShape(6.dp))
            )
        }

        Box(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
                .height(180.dp)
                .background(brush = brush, shape = RoundedCornerShape(6.dp))
        )

        Column(
            modifier = Modifier
                .padding(vertical = 20.dp, horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp),
        ) {

            Spacer(
                modifier = Modifier
                    .height(26.dp)
                    .width(200.dp)
                    .background(brush, shape = RoundedCornerShape(12.dp))
            )

            Spacer(
                modifier = Modifier
                    .height(26.dp)
                    .width(125.dp)
                    .background(brush, shape = RoundedCornerShape(12.dp))
            )

            Spacer(
                modifier = Modifier
                    .height(26.dp)
                    .fillMaxWidth()
                    .background(brush, shape = RoundedCornerShape(12.dp))
            )

            Spacer(
                modifier = Modifier
                    .height(26.dp)
                    .fillMaxWidth()
                    .background(brush, shape = RoundedCornerShape(12.dp))
            )

            Spacer(
                modifier = Modifier
                    .height(26.dp)
                    .fillMaxWidth()
                    .background(brush, shape = RoundedCornerShape(12.dp))
            )

        }
    }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
private fun SuccessContent(
    flower: FlowerDetail,
    isSaved: Boolean,
    onSave: () -> Unit
) {
    Column(
        modifier = Modifier.verticalScroll(
            rememberScrollState()
        )
    ) {
        Box(
            modifier = Modifier
                .height(58.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "${flower.fMonth}월 ${flower.fDay}일",
                style = MaterialTheme.typography.bodyLarge,
                color = Gray11
            )
        }

        val imgList = listOf(
            flower.imgUrl1,
            flower.imgUrl2,
            flower.imgUrl3
        )

        val imgState = rememberPagerState()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .height(180.dp)
                .padding(horizontal = 20.dp)
        ) {
            HorizontalPager(
                count = imgList.size,
                state = imgState,
                modifier = Modifier.clip(RoundedCornerShape(8.dp))
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(Utils.setImageUrl(url = imgList[it]))
                        .crossfade(true)
                        .build(),
                    contentDescription = flower.fileName1,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                    placeholder = painterResource(id = R.drawable.ic_loading_image)
                )
            }

            HorizontalPagerIndicator(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(20.dp),
                pagerState = imgState,
                activeColor = MaterialTheme.colorScheme.primary,
                inactiveColor = PrimaryAlpha50
            )
        }

        Column(
            modifier = Modifier
                .padding(vertical = 20.dp, horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(26.dp),
        ) {

            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                flower.flowLang?.split(", ")?.map {
                    Badge(it)
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    flower.flowNm?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.headlineMedium,
                            color = Gray11
                        )
                    }

                    Image(
                        painter = painterResource(id = R.drawable.ic_empty_heart_outline),
                        modifier = Modifier
                            .size(24.dp)
                            .noRippleClickable {
                                onSave()
                            },
                        colorFilter = if (isSaved) ColorFilter.tint(SystemRed) else null,
                        contentDescription = null
                    )

                }

                flower.fEngNm?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Gray6
                    )
                }
            }

            flower.fSctNm?.let {
                ChildContent(title = "학명", content = it)
            }

            flower.flowLang?.let {
                ChildContent(title = "꽃말", content = it)
            }

            flower.fContent?.let {
                ChildContent(title = "내용", content = it)
            }

            flower.fType?.let {
                ChildContent(title = "자생지", content = it)
            }

            flower.fUse?.let {
                ChildContent(title = "이용", content = it)
            }

            flower.fGrow?.let {
                ChildContent(title = "키우는 방법", content = it)
            }

            flower.publishOrg?.let {
                Text(
                    text = "[출처] $it",
                    color = Gray4,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(bottom = 24.dp, end = 6.dp)
                )
            }
        }
    }
}

@Composable
private fun ChildContent(title: String, content: String) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(title, style = MaterialTheme.typography.titleMedium, color = Gray10)
        Text(content, style = MaterialTheme.typography.bodyLarge, color = Gray9)
    }
}