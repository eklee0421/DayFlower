package com.nyang.dayFlower.presentation.flowerDetailInfo

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun FlowerDetailInfoScreen(
    viewModel : FlowerDetailInfoViewModel = hiltViewModel()
) {
}

@Composable
private fun FlowerDetailInfoContent() {

}

@Preview
@Composable
private fun FlowerDetailInfoPreview() {
    FlowerDetailInfoContent()
}

