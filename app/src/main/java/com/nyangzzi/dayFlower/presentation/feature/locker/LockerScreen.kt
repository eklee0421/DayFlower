package com.nyangzzi.dayFlower.presentation.feature.locker

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.firebase.auth.FirebaseUser
import com.nyangzzi.dayFlower.R
import com.nyangzzi.dayFlower.data.network.ResultWrapper
import com.nyangzzi.dayFlower.domain.model.common.FlowerDetail
import com.nyangzzi.dayFlower.presentation.base.component.FlowerCard
import com.nyangzzi.dayFlower.presentation.base.component.FlowerCardSize
import com.nyangzzi.dayFlower.ui.theme.Gray11
import com.nyangzzi.dayFlower.ui.theme.Gray5
import com.nyangzzi.dayFlower.ui.theme.Gray7
import com.nyangzzi.dayFlower.ui.theme.White

@Composable
fun LockerScreen() {

    val viewModel: LockerViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(White)
    ) {

        Image(
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth,
            painter = painterResource(id = R.drawable.img_locker_top_background),
            contentDescription = null
        )

        LockerContent(
            uiState = uiState,
            savedFlower = uiState.savedFlower,
            onEvent = viewModel::onEvent
        )
    }
}

@Composable
private fun LockerContent(
    uiState: LockerUiState,
    savedFlower: List<FlowerDetail>, onEvent: (LockerEvent) -> Unit
) {

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
        /*.verticalScroll(rememberScrollState())*/,
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {

        Top(user = uiState.user)

        SavedFlower(
            flower = savedFlower,
            savedFlower = uiState.savedFlower,
            isShowDetail = uiState.isShowDetail,
            setShowDetail = {
                onEvent(LockerEvent.SetShowDetail(it))
            }
        )
    }
}

@Composable
private fun Top(user: FirebaseUser?) {
    Box(modifier = Modifier.fillMaxWidth()) {

        Image(
            painter = painterResource(id = R.drawable.ic_locker_left_flower),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 12.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.ic_locker_right_flower),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 55.dp)
        )

        Text(
            text = "${user?.displayName}님의 기분 좋은 하루가\n차곡 차곡 쌓이고 있어요!",
            style = MaterialTheme.typography.headlineSmall,
            color = Gray11,
            modifier = Modifier.padding(top = 75.dp, start = 20.dp)
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SavedFlower(
    flower: List<FlowerDetail>,
    savedFlower: List<FlowerDetail>,
    isShowDetail: Boolean,
    setShowDetail: (Boolean) -> Unit,
) {

    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {

        if (flower.isNotEmpty()) {
            Text(
                text = "${flower.size}개의 꽃이 저장되었습니다.",
                color = Gray7,
                style = MaterialTheme.typography.labelSmall
            )
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_error_info),
                    contentDescription = null
                )
                Text(
                    text = "저장된 꽃이 없습니다",
                    color = Gray5,
                    style = MaterialTheme.typography.bodyMedium
                )

            }
        }

        var selectedItem by remember { mutableStateOf(-1) }

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {

            LazyVerticalGrid(
                columns = GridCells.Adaptive(152.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                contentPadding = PaddingValues(bottom = 20.dp)
            ) {
                itemsIndexed(flower) { _, item ->
                    FlowerCard(
                        flower = ResultWrapper.Success(item),
                        cardSize = FlowerCardSize.SMALL,
                        savedFlower = savedFlower,
                        isShowDetail = isShowDetail && item.dataNo == selectedItem,
                        setShowDetail = { isShown, dataNo ->
                            selectedItem = dataNo
                            setShowDetail(isShown)
                        }
                    )
                }
            }
        }

    }
}

@Preview
@Composable
private fun ContentPreview() {
    LockerScreen()
}
