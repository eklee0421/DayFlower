package com.nyangzzi.dayFlower.presentation.base.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.nyangzzi.dayFlower.R
import com.nyangzzi.dayFlower.data.network.ResultWrapper
import com.nyangzzi.dayFlower.domain.model.common.FlowerDetail
import com.nyangzzi.dayFlower.presentation.base.Utils
import com.nyangzzi.dayFlower.presentation.base.dialog.FlowerDetailDialog
import com.nyangzzi.dayFlower.ui.theme.Gray1
import com.nyangzzi.dayFlower.ui.theme.Gray5
import com.nyangzzi.dayFlower.ui.theme.Gray6
import com.nyangzzi.dayFlower.ui.theme.Gray9
import com.nyangzzi.dayFlower.ui.theme.PrimaryAlpha50
import com.nyangzzi.dayFlower.ui.theme.White

@Composable
fun FlowerCardLarge(
    flower: ResultWrapper<FlowerDetail>,
    onRefresh: () -> Unit = {},
    isShowDetail: Boolean,
    setShowDetail: (Boolean) -> Unit
) {

    when (flower) {
        is ResultWrapper.Loading -> {
            LoadingContent()
        }

        is ResultWrapper.Success -> {
            SuccessContent(flower.data, showDetail = { setShowDetail(true) })
            if (isShowDetail) {
                FlowerDetailDialog(flowerDetail = flower.data) {
                    setShowDetail(false)
                }
            }
        }

        is ResultWrapper.Error -> {
            ErrorContent(onRefresh = onRefresh)
        }
    }

}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun SuccessContent(flower: FlowerDetail, showDetail: () -> Unit = {}) {

    Column(
        modifier = Modifier
            .background(color = White, shape = RoundedCornerShape(12.dp))
            .border(width = 2.dp, color = Gray1, shape = RoundedCornerShape(12.dp))
            .clip(shape = RoundedCornerShape(12.dp))
            .clickable { showDetail() }
    ) {

        val imgList = listOf(
            flower.imgUrl1,
            flower.imgUrl2,
            flower.imgUrl3
        )

        val imgState = rememberPagerState()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .height(220.dp)
        ) {
            HorizontalPager(
                count = imgList.size,
                state = imgState,
                modifier = Modifier.clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(Utils.setImageUrl(imgList[it]))
                        .crossfade(true)
                        .build(),
                    contentDescription = flower.fileName1,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
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
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                flower.flowLang?.replace(" ", "")?.split(',')?.map {
                    Badge(it)
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                flower.flowNm?.let { Text(text = it) }
                flower.fEngNm?.let { Text(text = it, color = Gray6) }
            }

            flower.fContent?.let {
                Text(
                    text = "${it.substringBefore('.')}.",
                    color = Gray6,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

        }
    }

}


@Composable
private fun LoadingContent() {

    val brush = loadingShimmerEffect()

    Column(
        modifier = Modifier
            .background(color = White, shape = RoundedCornerShape(12.dp))
            .border(width = 2.dp, color = Gray1, shape = RoundedCornerShape(12.dp)),
    ) {

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .background(brush, shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 20.dp)
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
        }
    }
}

@Composable
private fun ErrorContent(onRefresh: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .background(color = White, shape = RoundedCornerShape(12.dp))
            .border(width = 2.dp, color = Gray1, shape = RoundedCornerShape(12.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .background(Gray1, shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_empty_img),
                contentDescription = "empty_img"
            )
        }

        Column(
            modifier = Modifier.height(130.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(onClick = { onRefresh() }) {
                Icon(imageVector = Icons.Rounded.Refresh, contentDescription = null, tint = Gray5)
            }
            Text("조회에 실패했습니다. 다시 시도해주세요", color = Gray9)
        }
    }
}


@Preview
@Composable
fun PreviewSuccessFlowerCard() {
    SuccessContent(
        flower = FlowerDetail(
            flowLang = "꽃말1, 꽃말2",
            flowNm = "꽃명",
            fEngNm = "영문",
            fContent = "설명입니다."
        )
    )
}

@Preview
@Composable
fun PreviewLoadingFlowerCard() {
    LoadingContent()
}

@Preview
@Composable
fun PreviewErrorFlowerCard() {
    ErrorContent()
}