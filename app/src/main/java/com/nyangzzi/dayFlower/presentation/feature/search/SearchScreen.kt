package com.nyangzzi.dayFlower.presentation.feature.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun SearchScreen() {
    Text(
        "검색", modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)
    )
}