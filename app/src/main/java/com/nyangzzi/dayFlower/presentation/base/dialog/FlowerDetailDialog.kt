package com.nyangzzi.dayFlower.presentation.base.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.nyangzzi.dayFlower.R
import com.nyangzzi.dayFlower.domain.model.common.FlowerDetail
import com.nyangzzi.dayFlower.presentation.base.Utils
import com.nyangzzi.dayFlower.presentation.base.component.Badge
import com.nyangzzi.dayFlower.ui.theme.Gray10
import com.nyangzzi.dayFlower.ui.theme.Gray11
import com.nyangzzi.dayFlower.ui.theme.Gray6
import com.nyangzzi.dayFlower.ui.theme.Gray9
import com.nyangzzi.dayFlower.ui.theme.PrimaryAlpha50
import com.nyangzzi.dayFlower.ui.theme.White

@Composable
fun FlowerDetailDialog(flowerDetail: FlowerDetail, onDismiss: () -> Unit) {

    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        )
    ) {
        FlowerDetailContent(flowerDetail = flowerDetail, onDismiss = onDismiss)
    }

}

@Composable
private fun FlowerDetailContent(flowerDetail: FlowerDetail, onDismiss: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White)
    ) {
        Top(onDismiss = onDismiss)
        Body(flowerDetail)
    }
}

@Composable
private fun Top(onDismiss: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        IconButton(onClick = onDismiss, modifier = Modifier.size(56.dp)) {
            Image(painter = painterResource(id = R.drawable.ic_close), contentDescription = null)
        }
        Text(
            text = stringResource(id = R.string.app_name),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun Body(flower: FlowerDetail) {
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
            Text("${flower.fMonth}월 ${flower.fDay}일")
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
                .width(320.dp)
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
            modifier = Modifier
                .padding(vertical = 20.dp, horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp),
        ) {

            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                flower.flowLang?.replace(" ", "")?.split(',')?.map {
                    Badge(it)
                }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                flower.flowNm?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.titleMedium,
                        color = Gray11
                    )
                }
                flower.fEngNm?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium,
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
        }
    }
}

@Composable
private fun ChildContent(title: String, content: String) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(title, style = MaterialTheme.typography.labelMedium, color = Gray10)
        Text(content, style = MaterialTheme.typography.bodyMedium, color = Gray9)
    }
}