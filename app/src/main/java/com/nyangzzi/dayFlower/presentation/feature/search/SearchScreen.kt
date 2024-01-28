package com.nyangzzi.dayFlower.presentation.feature.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nyangzzi.dayFlower.R
import com.nyangzzi.dayFlower.data.network.ResultWrapper
import com.nyangzzi.dayFlower.domain.model.common.FlowerDetail
import com.nyangzzi.dayFlower.presentation.base.component.FlowerCard
import com.nyangzzi.dayFlower.presentation.base.component.FlowerCardSize
import com.nyangzzi.dayFlower.presentation.base.component.SearchTextField
import com.nyangzzi.dayFlower.ui.theme.Gray11
import com.nyangzzi.dayFlower.ui.theme.Gray5
import com.nyangzzi.dayFlower.ui.theme.Primary
import com.nyangzzi.dayFlower.ui.theme.White

@Composable
fun SearchScreen() {

    val viewModel: SearchViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(White)
    ) {
        SearchContent(uiState = uiState, onEvent = viewModel::onEvent)
    }
}

@Composable
private fun SearchContent(uiState: SearchUiState, onEvent: (SearchEvent) -> Unit) {
    Column(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {

        when (uiState.flowerList) {
            ResultWrapper.None -> BeforeSearch(uiState = uiState, onEvent = onEvent)
            else -> AfterSearch(uiState = uiState, onEvent = onEvent)
        }
    }

}

@Composable
private fun BeforeSearch(uiState: SearchUiState, onEvent: (SearchEvent) -> Unit) {

    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        Row(
            modifier = Modifier.padding(top = 16.dp, bottom = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {

            SearchTapType.values().map { type ->
                TextButton(onClick = { onEvent(SearchEvent.UpdateSelectedType(type)) }) {
                    Text(
                        text = type.title,
                        color = if (uiState.selectedType == type) Gray11 else Gray5,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }

        SearchTextField(text = uiState.searchWord,
            onValueChange = { onEvent(SearchEvent.UpdateSearchWord(it)) },
            placeholder = "찾고 싶은 ${uiState.selectedType.title}을 검색해보세요",
            onSearch = { onEvent(SearchEvent.SearchFlowerList) })
    }
}

@Composable
private fun AfterSearch(uiState: SearchUiState, onEvent: (SearchEvent) -> Unit) {

    Column {
        Spacer(modifier = Modifier.height(30.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.padding(end = 20.dp)
        ) {
            IconButton(
                onClick = {
                    onEvent(SearchEvent.ClearFlowerList)
                }
            ) {
                Icon(
                    Icons.Rounded.ArrowBack,
                    contentDescription = null,
                    tint = Gray5,
                    modifier = Modifier.size(24.dp),
                )
            }

            SearchTextField(
                text = uiState.searchWord,
                onValueChange = { onEvent(SearchEvent.UpdateSearchWord(it)) },
                placeholder = "찾고 싶은 ${uiState.selectedType.title}을 검색해보세요",
                onSearch = { onEvent(SearchEvent.SearchFlowerList) },
                isSearchIcon = false,
                badge = uiState.selectedType.title
            )
        }

        when (uiState.flowerList) {
            is ResultWrapper.Error -> ErrorFlower(msg = uiState.flowerList.errorMessage) {
                onEvent(SearchEvent.SearchFlowerList)
            }

            is ResultWrapper.Loading -> LoadingFlower()
            is ResultWrapper.Success -> SuccessSearchFlower(uiState.flowerList.data)
            else -> {}
        }
    }

}

@Composable
private fun SuccessSearchFlower(flower: List<FlowerDetail>) {

    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .padding(top = 20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {

        if (flower.isNotEmpty()) {
            Text(
                text = "${flower.size}개의 꽃이 검색되었습니다.",
                color = Gray5,
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
                    text = "검색된 결과가 없습니다",
                    color = Gray5,
                    style = MaterialTheme.typography.bodyMedium
                )

            }
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
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
                        cardSize = FlowerCardSize.SMALL
                    )
                }
            }
        }

    }
}

@Composable
private fun ErrorFlower(msg: String, retry: () -> Unit) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.ic_error_info), contentDescription = null)
        Text(text = msg, color = Gray5, style = MaterialTheme.typography.bodyMedium)

        Button(
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Primary,
                contentColor = Gray11
            ),
            onClick = { retry() }) {
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Icon(imageVector = Icons.Rounded.Refresh, contentDescription = null)
                Text(text = "재시도")
            }

        }
    }
}

@Composable
private fun LoadingFlower() {
    LazyVerticalGrid(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .padding(top = 20.dp),
        columns = GridCells.Adaptive(152.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        contentPadding = PaddingValues(bottom = 20.dp)
    ) {
        items(4) {
            FlowerCard(
                flower = ResultWrapper.Loading,
                cardSize = FlowerCardSize.SMALL
            )
        }
    }
}
