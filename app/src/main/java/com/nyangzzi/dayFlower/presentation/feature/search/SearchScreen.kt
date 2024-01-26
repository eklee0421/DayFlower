package com.nyangzzi.dayFlower.presentation.feature.search

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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nyangzzi.dayFlower.data.network.ResultWrapper
import com.nyangzzi.dayFlower.presentation.base.component.SearchTextField
import com.nyangzzi.dayFlower.ui.theme.Gray11
import com.nyangzzi.dayFlower.ui.theme.Gray5
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
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
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

    }

}