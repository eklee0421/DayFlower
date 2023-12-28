package com.nyang.dayFlower.presentation.base.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.nyang.dayFlower.data.network.ResultWrapper
import com.nyang.dayFlower.domain.model.common.FlowerDetail
import com.nyang.dayFlower.presentation.base.Utils
import com.nyang.dayFlower.ui.theme.Gray1
import com.nyang.dayFlower.ui.theme.Gray6
import com.nyang.dayFlower.ui.theme.White

@Composable
fun FlowerCard(flower: ResultWrapper<FlowerDetail>) {

    when (flower) {
        is ResultWrapper.Loading -> {
            LoadingContent()
        }

        is ResultWrapper.Success -> {
            SuccessContent(flower.data)
        }

        is ResultWrapper.Error -> {

        }
    }

}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun SuccessContent(flower: FlowerDetail) {

    Column(
        modifier = Modifier
            .background(color = White, shape = RoundedCornerShape(12.dp))
            .border(width = 2.dp, color = Gray1, shape = RoundedCornerShape(12.dp))
    ) {

        val imgList = listOf(
            flower.imgUrl1,
            flower.imgUrl2,
            flower.imgUrl3
        )

        HorizontalPager(
            count = imgList.size,
            modifier = Modifier.clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(Utils.setImageUrl(imgList[it]))
                    .crossfade(true)
                    .build(),
                contentDescription = flower.fileName1,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .height(220.dp)
            )
        }

        Column(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {

            }

            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                flower.flowNm?.let { Text(text = it) }
                flower.fEngNm?.let { Text(text = it, color = Gray6) }
            }

            flower.fContent?.let { Text(text = "${it.substringBefore('.')}.", color = Gray6) }

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


@Preview
@Composable
fun PreviewFlowerCard() {
    SuccessContent(flower = FlowerDetail())
}