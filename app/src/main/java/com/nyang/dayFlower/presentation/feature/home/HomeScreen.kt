package com.nyang.dayFlower.presentation.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nyang.dayFlower.R
import com.nyang.dayFlower.ui.theme.White

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

        HomeContent()
    }

}

@Composable
private fun HomeContent() {

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        HomeTop()
        Spacer(modifier = Modifier.height(14.dp))
        TodayFlower()
    }
}

@Composable
private fun HomeTop() {

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
            text = "OOO님, 오늘도 좋은 날이에요.\n오늘의 꽃을 추천드릴게요.",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(top = 65.dp, start = 20.dp)
        )
    }
}

@Composable
private fun TodayFlower() {
    
}