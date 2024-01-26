package com.nyangzzi.dayFlower.presentation.feature.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nyangzzi.dayFlower.R
import com.nyangzzi.dayFlower.data.network.ResultWrapper
import com.nyangzzi.dayFlower.ui.theme.Gray1
import com.nyangzzi.dayFlower.ui.theme.Gray11
import com.nyangzzi.dayFlower.ui.theme.Gray5
import com.nyangzzi.dayFlower.ui.theme.Gray6
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
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {

        when (uiState.flowerList) {
            is ResultWrapper.Error -> TODO()
            ResultWrapper.Loading -> TODO()
            ResultWrapper.None -> BeforeSearch(uiState = uiState, onEvent = onEvent)
            is ResultWrapper.Success -> {}
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BeforeSearch(uiState: SearchUiState, onEvent: (SearchEvent) -> Unit) {

    Column {
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

        Row(
            modifier = Modifier
                .background(color = Gray1, shape = RoundedCornerShape(6.dp))
                .clickable {

                }
                .padding(start = 12.dp, end = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(painter = painterResource(id = R.drawable.ic_search), contentDescription = null)

            TextField(
                modifier = Modifier.weight(1f),
                value = uiState.searchWord, onValueChange = {
                    onEvent(SearchEvent.UpdateSearchWord(it))
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = { onEvent(SearchEvent.SearchFlowerList) }
                ),
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyMedium,
                placeholder = {
                    Text(
                        "찾고 싶은 ${uiState.selectedType.title}을 검색해보세요",
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    placeholderColor = Gray6,
                    textColor = Gray6
                )
            )

            IconButton(onClick = { onEvent(SearchEvent.UpdateSearchWord("")) }) {
                Image(
                    painter = painterResource(id = R.drawable.ic_cancel),
                    contentDescription = null
                )
            }
        }

    }
}